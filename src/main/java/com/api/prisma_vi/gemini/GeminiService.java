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
        GeminiRequestBody.Part part = new GeminiRequestBody.Part(prompt);
        GeminiRequestBody.Content content = new GeminiRequestBody.Content(Collections.singletonList(part));
        GeminiRequestBody geminiRequestBody = new GeminiRequestBody(Collections.singletonList(content));

        return geminiClient.searchColor(geminiRequestBody, apiToken).getBody();
    }

}
