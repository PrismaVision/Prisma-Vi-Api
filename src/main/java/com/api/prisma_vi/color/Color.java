package com.api.prisma_vi.color;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Color(
        @JsonProperty  String name,
        @JsonProperty  String hex,
        @JsonProperty  Rgb rgb,
        @JsonProperty  String hsl,
        @JsonProperty  Double luminance,
        @JsonProperty  Boolean isAccessibleOnWhite,
        @JsonProperty  Boolean isAccessibleOnBlack,
        @JsonProperty  String description,
        @JsonProperty List<String> psychologyTags,
        @JsonProperty  List<SimpleColor> colorPalette,
        @JsonProperty  String complementaryColor,
        @JsonProperty  List<String> hexVariations,
        @JsonProperty  String colorCategory,
        @JsonProperty  List<String> designUsageSuggestions
) {
}

