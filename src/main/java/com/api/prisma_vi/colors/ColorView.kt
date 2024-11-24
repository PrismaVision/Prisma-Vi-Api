package com.api.prisma_vi.colors

import com.fasterxml.jackson.annotation.JsonProperty

data class ColorView(
    @JsonProperty("name") val name: String,
    @JsonProperty("hexCode") val hexCode: String,
    @JsonProperty("rgbCode") val rgbCode: String,
    @JsonProperty("rybPercentages") val rybPercentages: RybPercentages,
    @JsonProperty("colorTemperature") val colorTemperature: String,
    @JsonProperty("colorDescription") val colorDescription: String,
    @JsonProperty("twoHexOfColorsThatMatch") val twoHexOfColorsThatMatch: List<String>,
    @JsonProperty("colorTerminology") val colorTerminology: String
)

data class RybPercentages(
    @JsonProperty("r") val r: String,
    @JsonProperty("y") val y: String,
    @JsonProperty("b") val b: String
)

data class ColorResponseWrapper(
    @JsonProperty("Color") val color: ColorView
)

