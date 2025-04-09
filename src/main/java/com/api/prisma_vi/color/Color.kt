package com.api.prisma_vi.color

class Color(
    val name: String? = null,
    val hexCode: String? = null,
    val rgb: Rgb? = null,
    val hsl: String? = null,
    val colorTemperature: String? = null,
    val colorDescription: String? = null,
    val twoColorsThatMatch: List<Color>? = null,
    val colorTerminology: String? = null
)

data class Rgb(
    val red: Int,
    val green: Int,
    val blue: Int,
)
