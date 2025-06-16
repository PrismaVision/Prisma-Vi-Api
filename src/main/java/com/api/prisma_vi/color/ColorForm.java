package com.api.prisma_vi.color;

import java.util.Arrays;
import java.util.Objects;

public record ColorForm(
        String name,
        String description,
        String[] psychologyTags,
        String designUsageSuggestions,
        SimpleColor[] colorPallete
) {
    // Construtor alternativo (sem argumentos) com valores padrão
    public ColorForm() {
        this(
                "Descriptive name of the color, e.g. 'Navy Blue'",
                "Brief emotional and cultural description of the color. e.g. 'Evokes seriousness and confidence.'",
                new String[] { "e.g. trust", "e.g. seriousness", "e.g. stability" },
                "Suggestions for use in design e.g. 'Useful as a background color in corporate apps'",
                new SimpleColor[] {
                        new SimpleColor("Name of the complementary or analogous color. e.g. 'Light Blue'", "e.g. #ADD8E6"),
                        new SimpleColor("Name another of the complementary or analogous color. e.g. 'Azul Claro'", "e.g. #ADD8E6")
                }
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColorForm other)) return false;
        return Objects.equals(name, other.name) &&
                Objects.equals(description, other.description) &&
                Arrays.equals(psychologyTags, other.psychologyTags) &&
                Objects.equals(designUsageSuggestions, other.designUsageSuggestions) &&
                Arrays.equals(colorPallete, other.colorPallete);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, designUsageSuggestions);
        result = 31 * result + Arrays.hashCode(psychologyTags);
        result = 31 * result + Arrays.hashCode(colorPallete);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"name\": \"").append(name).append("\",\n");
        sb.append("  \"description\": \"").append(description).append("\",\n");

        sb.append("  \"psychologyTags\": [\n");
        for (int i = 0; i < psychologyTags.length; i++) {
            sb.append("    \"").append(psychologyTags[i]).append("\"");
            if (i < psychologyTags.length - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ],\n");

        sb.append("  \"designUsageSuggestions\": \"").append(designUsageSuggestions).append("\",\n");

        sb.append("  \"colorPallete\": [\n");
        for (int i = 0; i < colorPallete.length; i++) {
            SimpleColor color = colorPallete[i];
            sb.append("    {\n");
            sb.append("      \"name\": \"").append(color.name()).append("\",\n");
            sb.append("      \"hex\": \"").append(color.hex()).append("\"\n");
            sb.append("    }");
            if (i < colorPallete.length - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n");
        sb.append("}");

        return sb.toString();
    }
}
