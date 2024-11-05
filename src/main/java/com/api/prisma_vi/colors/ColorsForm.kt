package com.api.prisma_vi.colors

data class ColorsForm(val name: String?,
                      val hexCode: String,
                      val rgbCode: String?,
                      val rybPercentages: String?,
                      val colorTemperature: String?,
                      val colorDescription: String?,
                      val twoHexOfColorsThatMatch: String?,
                      val colorTerminology: String?) {
    override fun toString(): String {
        return "\n \"Color\": {\n\"name\": $name,\n\"hexCode\": '$hexCode',\n\"rgbCode\": $rgbCode,\n\"rybPercentages\": $rybPercentages,\n\"colorTemperature\": $colorTemperature,\n\"colorDescription\": $colorDescription,\n\"twoHexOfColorsThatMatch\": $twoHexOfColorsThatMatch,\n\"colorTerminology\": $colorTerminology\n}"
    }
}
