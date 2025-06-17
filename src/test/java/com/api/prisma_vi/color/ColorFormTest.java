package com.api.prisma_vi.color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorFormTest {

    @Test
    public void testToStringReturnsProperJsonLikeFormat() {
        SimpleColor[] palette = new SimpleColor[] {
                new SimpleColor("Light Blue", "#ADD8E6"),
                new SimpleColor("Sky Blue", "#87CEEB")
        };

        ColorForm colorForm = new ColorForm(
                "Navy Blue",
                "Evokes seriousness and confidence.",
                new String[] { "trust", "seriousness", "stability" },
                "Useful as a background color in corporate apps",
                palette
        );

        String expected = """
                {
                  "name": "Navy Blue",
                  "description": "Evokes seriousness and confidence.",
                  "psychologyTags": ["trust","seriousness","stability"],
                  "designUsageSuggestions": "Useful as a background color in corporate apps",
                  "colorPalette": [
                    {"name": "Light Blue", "hex": "##ADD8E6"},
                    {"name": "Sky Blue", "hex": "##87CEEB"}
                  ]
                }
                """;

        String actual = colorForm.toString();

        assertEquals(expected.trim(), actual.trim());
    }
}