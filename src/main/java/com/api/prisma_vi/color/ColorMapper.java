package com.api.prisma_vi.color;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ColorMapper {

    private final ColorService colorService;

    public ColorMapper(ColorService colorService) {
        this.colorService = colorService;
    }

    public ColorView formToView(ColorForm form, String hex) {
        Rgb rgb = colorService.hexToRGB(hex);

        List<String> psychologyTags = Arrays.asList(form.psychologyTags());

        List<SimpleColor> palette = Arrays.stream(form.colorPalette())
                .map(c -> new SimpleColor(c.name(), c.hex()))
                .collect(Collectors.toList());

        List<String> usageSuggestions = form.designUsageSuggestions() != null
                ? Arrays.asList(form.designUsageSuggestions().split("\n"))
                : List.of();

        Color color = new Color(
                Objects.requireNonNull(form.name(), "Color name cannot be null"),
                hex,
                rgb,
                colorService.rgbToHSL(rgb.red(), rgb.green(), rgb.blue()),
                colorService.calculateLuminance(rgb.red(), rgb.green(), rgb.blue()),
                colorService.isAccessibleOnWhite(rgb.red(), rgb.green(), rgb.blue()),
                colorService.isAccessibleOnBlack(rgb.red(), rgb.green(), rgb.blue()),
                form.description() != null ? form.description() : "Sem descrição.",
                psychologyTags,
                palette,
                colorService.getComplementaryColor(hex),
                colorService.getHexVariations(hex),
                colorService.inferColorCategory(hex),
                usageSuggestions
        );

        return new ColorView(color);
    }
}
