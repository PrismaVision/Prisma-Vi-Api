package com.api.prisma_vi.colors

class Color(
    val name: String? = null,
    val hexCode: String? = null,
    val rgb: Rgb? = null,
    val ryb: Ryb? = null,
    val colorTemperature: String? = null,
    val colorDescription: String? = null,
    val twoColorsThatMatch: List<Color>? = null,
    val colorTerminology: String? = null
)

data class Rgb(
    val red: Double,
    val green: Double,
    val blue: Double,
)
data class Ryb(
    val red: Double,
    val yellow: Double,
    val blue: Double,
)