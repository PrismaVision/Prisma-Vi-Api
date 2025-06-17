package com.api.prisma_vi.gemini;

import com.api.prisma_vi.color.*;
import com.api.prisma_vi.gemini.feign.GeminiClient;
import com.api.prisma_vi.utils.apiError.InvalidHexadecimalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GeminiService {

    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);

    private final ColorService colorService;
    private final GeminiClient geminiClient;
    private final ColorMapper colorMapper;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.token}")
    private String apiToken;

    public GeminiService(ColorService colorService, GeminiClient geminiClient, ColorMapper colorMapper, ObjectMapper objectMapper) {
        this.colorService = colorService;
        this.geminiClient = geminiClient;
        this.colorMapper = colorMapper;
        this.objectMapper = objectMapper;
    }

    private String generatePrompt(String hex){

        String[] languages = {"pt-br","en-eu"};

        ColorForm object = new ColorForm();

        return """
        You are an expert in color psychology, UI/UX, and design semantics.

        Given the hexadecimal color code: %s
        
        fill the following JSON object with real and meaningful data about this color:
        - Describe its psychological and emotional characteristics.
        - Suggest use cases in design and branding.
        - Name the color appropriately.
        - Provide at least two related colors (complementary or analogous) with their names and HEX values.
        - The entire response should be written in: %s
        
        Use the structure below as a template for the expected output (replace all example values):

        %s

        Return only the JSON object. Do not add explanations or introductions.
        """.formatted(hex, languages[0], object.toString());
    }


    private String generateContent(String prompt) {
        var part = new GeminiRequestBody.Part(prompt);
        var content = new GeminiRequestBody.Content(Collections.singletonList(part));
        var generationConfig = new GeminiRequestBody.GenerationConfig("application/json");
        var geminiRequestBody = new GeminiRequestBody(
                Collections.singletonList(content),
                generationConfig
        );
        ResponseEntity<String> responseEntity = geminiClient.searchColor(geminiRequestBody, apiToken);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            logger.error("Gemini API returned an error: {} - {}", responseEntity.getStatusCode(), responseEntity.getBody());
            throw new RuntimeException("Error calling Gemini API: " + responseEntity.getStatusCode());
        }
    }

    private String formatResponse(String jsonResponse) {
        try {
            GeminiResponseBody root = objectMapper.readValue(jsonResponse, GeminiResponseBody.class);
            return root.candidates()
                    .getFirst()
                    .content()
                    .parts()
                    .getFirst()
                    .text();

        } catch (Exception e) {
            logger.error("Error parsing GeminiResponseBody: {}", jsonResponse, e);
            throw new RuntimeException("Failed to parse Gemini API response.", e);
        }
    }

    public ColorForm responseToColorForm(String text) {
        try {
            return objectMapper.readValue(text, ColorForm.class);
        } catch (Exception e) {
            logger.error("Error mapping extracted Gemini text to GeminiColorResponse: {}", text, e);
            throw new RuntimeException("Failed to map Gemini response to GeminiColorResponse.", e);
        }
    }

    private ColorView colorFormToColorView(ColorForm form, String hex){
        return colorMapper.formToView(form, hex);
    }

    public ResponseEntity<?> validatedSearchColor(String hex) {
        try {
            colorService.validateHexColor(hex);
        } catch (InvalidHexadecimalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String trimmedHex = hex.trim();
        String prompt = generatePrompt(trimmedHex);
        String rawGeminiResponse = generateContent(prompt);
        String text = formatResponse(rawGeminiResponse);

        ColorForm geminiData = responseToColorForm(text);

        ColorView finalColorView = colorFormToColorView(geminiData, trimmedHex);

        return ResponseEntity.ok().body(finalColorView);
    }
}

