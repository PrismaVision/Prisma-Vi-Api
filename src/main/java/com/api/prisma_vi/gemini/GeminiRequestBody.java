package com.api.prisma_vi.gemini;

import java.util.List;

public record GeminiRequestBody(List<Content> contents) {

        public record Content(List<Part> parts) {}

        public record Part(String text) {}
}
