package com.api.prisma_vi.gemini;

public record GeminiColorResponse(
        GeminiColorDetails color
) {
    public record GeminiColorDetails(
            String name,
            String description,
            String[] psychologyTags,
            String[] designUsageSuggestions,
            GeminiSimpleColor[] colorPalette
    ) {}

    public record GeminiSimpleColor(String name, String hex) {}
}
