package com.api.prisma_vi.gemini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search-color")
public class GeminiController {

    private final GeminiService geminiService;

    @Autowired
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public String generate(@RequestBody String hex) {
        return geminiService.formatResponse(
                geminiService.generateContent(
                        geminiService.generatePrompt(hex.trim())));
    }
}
