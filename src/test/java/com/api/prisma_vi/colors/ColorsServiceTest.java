package com.api.prisma_vi.colors;

import com.api.prisma_vi.utils.apiError.InvalidHexadecimalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColorsServiceTest {

    private ColorsService colorsService;

    @BeforeEach
    public void setUp() {
        colorsService = new ColorsService();
    }

    @Test
    public void testValidateHexColor_ValidHex() {
        assertDoesNotThrow(() -> colorsService.validateHexColor("#FFFFFF"));
    }

    @Test
    public void testValidateHexColor_InvalidHex() {
        assertThrows(InvalidHexadecimalException.class, () -> colorsService.validateHexColor("G12G12"));
    }

    @Test
    public void testHexToRGB() {
        Rgb rgb = colorsService.hexToRGB("#00008B");
        assertEquals(0, rgb.getRed());
        assertEquals(0, rgb.getGreen());
        assertEquals(139, rgb.getBlue());
    }

    @Test
    public void testCalculateLuminance() {
        double luminance = colorsService.calculateLuminance(0, 0, 139);
        assertTrue(luminance >= 0 && luminance <= 1);
    }

    @Test
    public void testIsAccessibleOnWhite() {
        assertTrue(colorsService.isAccessibleOnWhite(0, 0, 139)); // Azul escuro
    }

    @Test
    public void testIsAccessibleOnBlack() {
        assertFalse(colorsService.isAccessibleOnBlack(0, 0, 139)); // Azul escuro
    }

    @Test
    public void testRgbToHSL() {
        String hsl = colorsService.rgbToHSL(0, 0, 139);
        assertNotNull(hsl);
        assertTrue(hsl.contains(","));
    }

    @Test
    public void testGetComplementaryColor() {
        String complementary = colorsService.getComplementaryColor("#00008B");
        assertTrue(complementary.startsWith("#"));
    }

    @Test
    public void testInferColorCategory() {
        String category = colorsService.inferColorCategory("#FF0000"); // vermelho
        assertEquals("Primária (Vermelho)", category);
    }
}
