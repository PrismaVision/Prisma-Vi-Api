package com.api.prisma_vi.color;

import com.api.prisma_vi.utils.apiError.InvalidHexadecimalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColorServiceTest {

    private ColorService colorService;

    @BeforeEach
    public void setUp() {
        colorService = new ColorService();
    }

    @Test
    public void testValidateHexColor_ValidHex() {
        assertDoesNotThrow(() -> colorService.validateHexColor("#FFFFFF"));
    }

    @Test
    public void testValidateHexColor_InvalidHex() {
        assertThrows(InvalidHexadecimalException.class, () -> colorService.validateHexColor("G12G12"));
    }

    @Test
    public void testHexToRGB() {
        Rgb rgb = colorService.hexToRGB("#00008B");
        assertEquals(0, rgb.getRed());
        assertEquals(0, rgb.getGreen());
        assertEquals(139, rgb.getBlue());
    }

    @Test
    public void testCalculateLuminance() {
        double luminance = colorService.calculateLuminance(0, 0, 139);
        assertTrue(luminance >= 0 && luminance <= 1);
    }

    @Test
    public void testIsAccessibleOnWhite() {
        assertTrue(colorService.isAccessibleOnWhite(0, 0, 139)); // Azul escuro
    }

    @Test
    public void testIsAccessibleOnBlack() {
        assertFalse(colorService.isAccessibleOnBlack(0, 0, 139)); // Azul escuro
    }

    @Test
    public void testRgbToHSL() {
        String hsl = colorService.rgbToHSL(0, 0, 139);
        assertNotNull(hsl);
        assertTrue(hsl.contains(","));
    }

    @Test
    public void testGetComplementaryColor() {
        String complementary = colorService.getComplementaryColor("#00008B");
        assertTrue(complementary.startsWith("#"));
    }

    @Test
    public void testInferColorCategory() {
        String category = colorService.inferColorCategory("#FF0000"); // vermelho
        assertEquals("Primária (Vermelho)", category);
    }
}
