package com.api.prisma_vi.color

import org.springframework.stereotype.Component

@Component
class ColorMapper(private val colorService: ColorService) {

    fun formToView(form: ColorForm, hex: String): ColorView{
        val rgb = colorService.hexToRGB(hex)
        val view: ColorView = ColorView(
            Color(
                name = form.name!!,
                hex = hex,
                rgb = rgb,
                hsl = colorService.rgbToHSL(rgb.red, rgb.green, rgb.blue),
                luminance = colorService.calculateLuminance(rgb.red, rgb.green, rgb.blue),
                isAccessibleOnWhite = colorService.isAccessibleOnWhite(rgb.red, rgb.green, rgb.blue),
                isAccessibleOnBlack = colorService.isAccessibleOnBlack(rgb.red, rgb.green, rgb.blue),
                description = form.description ?: "Sem descrição.",
                psychologyTags = form.psychologyTags.toList(),
                colorPalette = form.colorPallete.map { SimpleColor(it.name, it.hex) },
                complementaryColor = colorService.getComplementaryColor(hex),
                hexVariations = colorService.getHexVariations(hex),
                colorCategory = colorService.inferColorCategory(hex),
                designUsageSuggestions = form.designUsageSuggestions?.split("\n") ?: emptyList()
            )
        )
        return view;
    }

}