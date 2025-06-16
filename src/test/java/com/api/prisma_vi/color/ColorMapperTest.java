package com.api.prisma_vi.color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ColorMapperTest {

    @Mock
    private ColorService colorService;

    @InjectMocks
    private ColorMapper colorMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFormToView_FullScenario() {
        ColorForm form = new ColorForm(
                "Vermelho Vibrante",
                "Uma cor vibrante que evoca paixão e energia.",
                new String[]{"Paixão", "Energia", "Amor"},
                "Uso em UI Design para botões de ação.\nIdeal para branding de produtos de luxo.",
                new SimpleColor[]{
                        new SimpleColor("Vermelho Claro", "#FFCCCC"),
                        new SimpleColor("Borgonha", "#800020")
                }
        );

        String hex = "#FF0000";

        Rgb mockedRgb = new Rgb(255, 0, 0);
        when(colorService.hexToRGB(hex)).thenReturn(mockedRgb);

        String mockedHsl = "0, 100%, 50%";
        when(colorService.rgbToHSL(mockedRgb.red(), mockedRgb.green(), mockedRgb.blue())).thenReturn(mockedHsl);

        double mockedLuminance = 0.2126;
        when(colorService.calculateLuminance(mockedRgb.red(), mockedRgb.green(), mockedRgb.blue())).thenReturn(mockedLuminance);

        when(colorService.isAccessibleOnWhite(mockedRgb.red(), mockedRgb.green(), mockedRgb.blue())).thenReturn(false);

        when(colorService.isAccessibleOnBlack(mockedRgb.red(), mockedRgb.green(), mockedRgb.blue())).thenReturn(true);

        when(colorService.getComplementaryColor(hex)).thenReturn("#00FFFF");

        List<String> mockedHexVariations = Arrays.asList("#FF3333", "#CC0000", "#FF0000");
        when(colorService.getHexVariations(hex)).thenReturn(mockedHexVariations);

        when(colorService.inferColorCategory(hex)).thenReturn("Primária (Vermelho)");


        ColorView colorView = colorMapper.formToView(form, hex);

        assertNotNull(colorView);
        Color color = colorView.color();
        assertNotNull(color);

        assertEquals("Vermelho Vibrante", color.name());
        assertEquals(hex, color.hex());
        assertEquals(mockedRgb, color.rgb());
        assertEquals(mockedHsl, color.hsl());
        assertEquals(mockedLuminance, color.luminance(), 0.0001);
        assertEquals(false, color.isAccessibleOnWhite());
        assertEquals(true, color.isAccessibleOnBlack());
        assertEquals("Uma cor vibrante que evoca paixão e energia.", color.description());
        assertEquals(Arrays.asList("Paixão", "Energia", "Amor"), color.psychologyTags());

        assertEquals(2, color.colorPalette().size());
        assertEquals("Vermelho Claro", color.colorPalette().get(0).name());
        assertEquals("#FFCCCC", color.colorPalette().get(0).hex());
        assertEquals("Borgonha", color.colorPalette().get(1).name());
        assertEquals("#800020", color.colorPalette().get(1).hex());

        assertEquals("#00FFFF", color.complementaryColor());
        assertEquals(mockedHexVariations, color.hexVariations());
        assertEquals("Primária (Vermelho)", color.colorCategory());
        assertEquals(Arrays.asList("Uso em UI Design para botões de ação.", "Ideal para branding de produtos de luxo."), color.designUsageSuggestions());
    }

    @Test
    void testFormToView_WithNullDescriptionAndEmptySuggestions() {
        ColorForm form = new ColorForm(
                "Azul Suave",
                null,
                new String[]{},
                null,
                new SimpleColor[]{}
        );
        String hex = "#ADD8E6";

        Rgb mockedRgb = new Rgb(173, 216, 230);
        when(colorService.hexToRGB(hex)).thenReturn(mockedRgb);
        when(colorService.rgbToHSL(anyInt(), anyInt(), anyInt())).thenReturn("195, 53%, 79%");
        when(colorService.calculateLuminance(anyInt(), anyInt(), anyInt())).thenReturn(0.686);
        when(colorService.isAccessibleOnWhite(anyInt(), anyInt(), anyInt())).thenReturn(true);
        when(colorService.isAccessibleOnBlack(anyInt(), anyInt(), anyInt())).thenReturn(false);
        when(colorService.getComplementaryColor(anyString())).thenReturn("#E6D8AD");
        when(colorService.getHexVariations(anyString())).thenReturn(Collections.emptyList());
        when(colorService.inferColorCategory(anyString())).thenReturn("Primária (Azul)");


        ColorView colorView = colorMapper.formToView(form, hex);


        assertNotNull(colorView);
        Color color = colorView.color();
        assertNotNull(color);

        assertEquals("Azul Suave", color.name());
        assertEquals("Sem descrição.", color.description());
        assertEquals(Collections.emptyList(), color.psychologyTags());
        assertEquals(Collections.emptyList(), color.colorPalette());
        assertEquals(Collections.emptyList(), color.designUsageSuggestions());
        }
    }