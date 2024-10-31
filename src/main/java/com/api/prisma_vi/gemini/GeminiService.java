package com.api.prisma_vi.gemini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GeminiService {

    private final GeminiClient geminiClient;

    @Value("${gemini.api.token}")
    private String apiToken;

    @Autowired
    public GeminiService(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }


    public String generateContent(String prompt) {
        var part = new GeminiRequestBody.Part(prompt);
        var content = new GeminiRequestBody.Content(Collections.singletonList(part));
        var generationConfig = new GeminiRequestBody.GenerationConfig("application/json");
        var geminiRequestBody = new GeminiRequestBody(
                Collections.singletonList(content),
                generationConfig
        );
        return geminiClient.searchColor(geminiRequestBody, apiToken).getBody();
    }

}
