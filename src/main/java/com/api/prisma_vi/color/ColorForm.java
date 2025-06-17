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
        String sb = """
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
                colorPallete[0].name(),
                colorPallete[0].hex(),
                colorPallete[1].name(),
                colorPallete[1].hex());

        return sb.toString();
    }
}
