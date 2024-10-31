package com.api.prisma_vi.gemini;

import java.util.List;

public record GeminiRequestBody(
        List<Content> contents,
        GenerationConfig generationConfig) {

        public record Content(List<Part> parts) {}

        public record Part(String text) {}

        public record GenerationConfig(String responseMimeType) {}
}
