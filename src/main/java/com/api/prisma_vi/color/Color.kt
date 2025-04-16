package com.api.prisma_vi.color

import com.fasterxml.jackson.annotation.JsonProperty

data class Color(
    @JsonProperty val name: String,
    @JsonProperty val hex: String,
    @JsonProperty val rgb: Rgb,
    @JsonProperty val hsl: String,
    @JsonProperty val luminance: Double,
    @JsonProperty val isAccessibleOnWhite: Boolean,
    @JsonProperty val isAccessibleOnBlack: Boolean,
    @JsonProperty val description: String,
    @JsonProperty val psychologyTags: List<String>,
    @JsonProperty val colorPalette: List<SimpleColor>,
    @JsonProperty val complementaryColor: String,
    @JsonProperty val hexVariations: List<String>,
    @JsonProperty val colorCategory: String,
    @JsonProperty val designUsageSuggestions: List<String>
)

data class Rgb(
    @JsonProperty val red: Int,
    @JsonProperty val green: Int,
    @JsonProperty val blue: Int
)

data class SimpleColor(
    @JsonProperty("name") val name: String,
    @JsonProperty("hex") val hex: String
)

