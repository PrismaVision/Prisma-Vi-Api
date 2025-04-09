package com.api.prisma_vi.color

data class ColorForm(val name: String?,
                     val hexCode: String,
                     val rgbCode: String?,
                     val ryb: String?,
                     val colorTemperature: String?,
                     val colorDescription: String?,
                     val twoHexOfColorsThatMatch: String?,
                     val colorTerminology: String?) {
    constructor() : this(
        name = "(the name of the closest common color)",
        hexCode = "(HEX code of color)",
        rgbCode = "(RGB code of color)",
        ryb = "(red yellow and blue percentages to make the color with this format: {r: x%, y: x%, b: x%})",
        colorTemperature = "(hot, cold or neutral)",
        colorDescription = "(a brief description of the color [color name], including its visual characteristics, how it is formed and what it conveys in terms of feelings, environments or objects that represent it, as well as examples of where this color can be found in nature or in the everyday)",
        twoHexOfColorsThatMatch = "(two colors that match with the main color in HEX code)",
        colorTerminology = "(primary, secondary, tertiary, neutral or terrestrial)"
    )
}
