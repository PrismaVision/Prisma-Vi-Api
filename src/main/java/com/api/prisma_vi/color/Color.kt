package com.api.prisma_vi.color

data class Color(
    val name: String,
    val hex: String,
    val rgb: Rgb,
    val hsl: Hsl,
    val luminance: Double,
    val isAccessibleOnWhite: Boolean,
    val isAccessibleOnBlack: Boolean,
    val description: String,
    val psychologyTags: List<String>,
    val colorPalette: List<SimpleColor>,
    val complementaryColors: List<String>,
    val hexVariations: List<String>,
    val colorCategory: String,
    val designUsageSuggestions: List<String>
)

data class Rgb(
    val red: Int,
    val green: Int,
    val blue: Int
)

data class Hsl(
    val hue: Int,
    val saturation: String,
    val lightness: String
)

data class SimpleColor(
    val name: String,
    val hex: String
)

