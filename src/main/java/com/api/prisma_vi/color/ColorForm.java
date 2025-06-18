package com.api.prisma_vi.color;

public record ColorForm(
        String name,
        String description,
        String[] psychologyTags,
        String designUsageSuggestions,
        SimpleColor[] colorPalette
) {
    public ColorForm() {
        this(
                "Descriptive_name_of_the_color,_e.g._'Navy_Blue'",
                "Brief_emotional_and_cultural_description_of_the_color._e.g._'Evokes_seriousness_and_confidence.'",
                new String[] { "e.g._trust", "e.g._seriousness", "e.g._stability" },
                "Suggestions_for_use_in_design_e.g._'Useful_as_a_background_color_in_corporate_apps'",
                new SimpleColor[] {
                        new SimpleColor("Name_of_the_complementary_or_analogous_color._e.g._'Light_Blue'", "e.g._#ADD8E6"),
                        new SimpleColor("Name_another_of_the_complementary_or_analogous_color._e.g._'Azul_Claro'", "e.g._#ADD8E6")
                }
        );
    }

    @Override
    public String toString() {

        return """
        {
          "name": "%s",
          "description": "%s",
          "psychologyTags": ["%s","%s","%s"],
          "designUsageSuggestions": "%s",
          "colorPalette": [
            {"name": "%s", "hex": "#%s"},
            {"name": "%s", "hex": "#%s"}
          ]
        }
        """.formatted(name,
                description,
                psychologyTags[0],
                psychologyTags[1],
                psychologyTags[2],
                designUsageSuggestions,
                colorPalette[0].name(),
                colorPalette[0].hex(),
                colorPalette[1].name(),
                colorPalette[1].hex());
    }
}
