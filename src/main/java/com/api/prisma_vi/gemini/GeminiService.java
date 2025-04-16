package com.api.prisma_vi.gemini;

import com.api.prisma_vi.color.ColorMapper;
import com.api.prisma_vi.color.ColorService;
import com.api.prisma_vi.color.ColorView;
import com.api.prisma_vi.gemini.feign.GeminiClient;
import com.api.prisma_vi.utils.apiError.InvalidHexadecimalException;
import com.api.prisma_vi.color.ColorForm;
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

    @Value("${gemini.api.token}")
    private String apiToken;

    public GeminiService(ColorService colorService, GeminiClient geminiClient, ColorMapper colorMapper) {
        this.colorService = colorService;
        this.geminiClient = geminiClient;
        this.colorMapper = colorMapper;
    }


    private String generatePrompt(String hex){

        String[] languages = {"pt-br","en-eu"};

        ColorForm object = new ColorForm();

        return """
        You are an expert in color psychology, UI/UX, and design semantics.

        Given the hexadecimal color code: %s
        
        Please fill the following JSON object with real and meaningful data about this color:
        - Describe its psychological and emotional characteristics.
        - Suggest use cases in design and branding.
        - Name the color appropriately.
        - Provide at least two related colors (complementary or analogous) with their names and HEX values.
        - The answers should be well-written and translated into English (en-US).
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
        return geminiClient.searchColor(geminiRequestBody, apiToken).getBody();
    }

    private String formatResponse(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            GeminiResponseBody root = mapper.readValue(jsonResponse, GeminiResponseBody.class);
            return root.candidates()
                    .getFirst()
                    .content()
                    .parts()
                    .getFirst()
                    .text();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ColorForm responseToColorForm(String jsonResponse) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonResponse, ColorForm.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ColorView colorFormToColorView(ColorForm form, String hex){
        return colorMapper.formToView(form, hex);
    }

    public ResponseEntity<?> validatedSearchColor(String hex){
        try {
            colorService.validateHexColor(hex);}
        catch (InvalidHexadecimalException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(
                colorFormToColorView(
                        responseToColorForm(
                                formatResponse(
                                        generateContent(
                                                generatePrompt(hex.trim()))
                                )
                        )
                        ,hex
                )

        );
    }
}

