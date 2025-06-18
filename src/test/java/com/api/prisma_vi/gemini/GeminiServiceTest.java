package com.api.prisma_vi.gemini;

import com.api.prisma_vi.color.*;
import com.api.prisma_vi.gemini.feign.GeminiClient;
import com.api.prisma_vi.utils.apiError.InvalidHexadecimalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GeminiServiceTest {

    @Mock
    private ColorService colorService;

    @Mock
    private GeminiClient geminiClient;

    @Mock
    private ColorMapper colorMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private GeminiService geminiService;

    private final String HEX_COLOR = "#FFFFFF";
    private final String API_TOKEN = "testApiToken";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(geminiService, "apiToken", API_TOKEN);
    }

    @Test
    void testGeneratePrompt() {
        ColorForm realColorForm = new ColorForm();

        String prompt = ReflectionTestUtils.invokeMethod(geminiService, "generatePrompt", HEX_COLOR);

        assertNotNull(prompt);
        assertTrue(prompt.contains(HEX_COLOR));
        assertTrue(prompt.contains("pt-br"));
        assertTrue(prompt.contains("You are an expert in color psychology, UI/UX, and design semantics."));
        assertTrue(prompt.contains("Return only the JSON object. Do not add explanations or introductions."));
        assertTrue(prompt.contains(realColorForm.toString().trim()));
    }


    @Test
    void testGenerateContent_Success() {
        String prompt = "Test prompt";
        String geminiApiResponse = """
                {
                  "candidates": [
                    {
                      "content": {
                        "parts": [
                          {
                            "text": "{\\n  \\"name\\": \\"Branco Puro\\",\\n  \\"description\\": \\"Representa pureza...\\"\\n}"
                          }
                        ]
                      },
                      "finishReason": "STOP",
                      "avgLogprobs": -0.29
                    }
                  ],
                  "usageMetadata": {
                    "promptTokenCount": 367,
                    "candidatesTokenCount": 174,
                    "totalTokenCount": 541,
                    "promptTokensDetails": [
                      {
                        "modality": "TEXT",
                        "tokenCount": 367
                      }
                    ],
                    "candidatesTokensDetails": [
                      {
                        "modality": "TEXT",
                        "tokenCount": 174
                      }
                    ]
                  },
                  "modelVersion": "gemini-2.0-flash-lite",
                  "responseId": "XP"
                }
                """;
        ResponseEntity<String> successResponse = new ResponseEntity<>(geminiApiResponse, HttpStatus.OK);

        when(geminiClient.searchColor(any(GeminiRequestBody.class), eq(API_TOKEN)))
                .thenReturn(successResponse);

        String content = ReflectionTestUtils.invokeMethod(geminiService, "generateContent", prompt);

        assertNotNull(content);
        assertEquals(geminiApiResponse, content);
        verify(geminiClient).searchColor(any(GeminiRequestBody.class), eq(API_TOKEN));
    }

    @Test
    void testGenerateContent_Error() {
        String prompt = "Test prompt";
        ResponseEntity<String> errorResponse = new ResponseEntity<>("Error details", HttpStatus.INTERNAL_SERVER_ERROR);

        when(geminiClient.searchColor(any(GeminiRequestBody.class), eq(API_TOKEN)))
                .thenReturn(errorResponse);

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                ReflectionTestUtils.invokeMethod(geminiService, "generateContent", prompt)
        );

        assertTrue(thrown.getMessage().contains("Error calling Gemini API: 500 INTERNAL_SERVER_ERROR"));
        verify(geminiClient).searchColor(any(GeminiRequestBody.class), eq(API_TOKEN));
    }

    @Test
    void testFormatResponse_Success() throws Exception {
        String jsonResponse = """
                {
                  "candidates": [
                    {
                      "content": {
                        "parts": [
                          {
                            "text": "{\\n  \\"name\\": \\"Branco Puro\\",\\n  \\"description\\": \\"Representa pureza, limpeza, e simplicidade. É frequentemente associado à inocência, paz e perfeição. No design, pode transmitir minimalismo e modernidade.\\",\\n  \\"psychologyTags\\": [\\"Pureza\\", \\"Limpeza\\", \\"Minimalismo\\", \\"Inocência\\"],\\n  \\"designUsageSuggestions\\": \\"Ideal para fundos, espaços negativos, e para destacar outros elementos. Usado em logos e sites para transmitir clareza e modernidade. Também é bom para layouts que buscam transmitir sensação de espaço e organização.\\",\\n  \\"colorPalette\\": [\\n    {\\"name\\": \\"Preto\\", \\"hex\\": \\"#000000\\"},\\n    {\\"name\\": \\"Cinza Claro\\", \\"hex\\": \\"#CCCCCC\\"}\\n  ]\\n}"
                          }
                        ],
                        "role": "model"
                      },
                      "finishReason": "STOP",
                      "avgLogprobs": -0.29390293428267555
                    }
                  ],
                  "usageMetadata": {
                    "promptTokenCount": 367,
                    "candidatesTokenCount": 174,
                    "totalTokenCount": 541
                  },
                  "modelVersion": "gemini-2.0-flash-lite",
                  "responseId": "XPxSaKHECKKp1dkP4575yAM"
                }
                """;

        String expectedExtractedText = """
                {
                  "name": "Branco Puro",
                  "description": "Representa pureza, limpeza, e simplicidade. É frequentemente associado à inocência, paz e perfeição. No design, pode transmitir minimalismo e modernidade.",
                  "psychologyTags": ["Pureza", "Limpeza", "Minimalismo", "Inocência"],
                  "designUsageSuggestions": "Ideal para fundos, espaços negativos, e para destacar outros elementos. Usado em logos e sites para transmitir clareza e modernidade. Também é bom para layouts que buscam transmitir sensação de espaço e organização.",
                  "colorPalette": [
                    {"name": "Preto", "hex": "#000000"},
                    {"name": "Cinza Claro", "hex": "#CCCCCC"}
                  ]
                }
                """;

        Part part = new Part(expectedExtractedText);
        Content content = new Content(Collections.singletonList(part), "model");
        Candidate candidate = new Candidate(content, "STOP", -0.29390293428267555);
        GeminiResponseBody geminiResponseBody = new GeminiResponseBody(Collections.singletonList(candidate), null, null, null);


        when(objectMapper.readValue(jsonResponse, GeminiResponseBody.class)).thenReturn(geminiResponseBody);

        String formattedText = ReflectionTestUtils.invokeMethod(geminiService, "formatResponse", jsonResponse);

        assertNotNull(formattedText);
        assertEquals(expectedExtractedText.trim(), formattedText.trim());
        verify(objectMapper).readValue(jsonResponse, GeminiResponseBody.class);
    }

    @Test
    void testFormatResponse_ErrorParsing() throws Exception {
        String invalidJsonResponse = "invalid json";

        when(objectMapper.readValue(eq(invalidJsonResponse), eq(GeminiResponseBody.class)))
                .thenThrow(new RuntimeException("JSON parsing error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                ReflectionTestUtils.invokeMethod(geminiService, "formatResponse", invalidJsonResponse)
        );

        assertTrue(thrown.getMessage().contains("Failed to parse Gemini API response."));
        verify(objectMapper).readValue(invalidJsonResponse, GeminiResponseBody.class);
    }

    @Test
    void testResponseToColorForm_Success() throws Exception {
        String geminiText = """
                {
                  "name": "Branco Puro",
                  "description": "Representa pureza, limpeza, e simplicidade. É frequentemente associado à inocência, paz e perfeição. No design, pode transmitir minimalismo e modernidade.",
                  "psychologyTags": ["Pureza","Limpeza","Minimalismo"],
                  "designUsageSuggestions": "Ideal para fundos, espaços negativos, e para destacar outros elementos. Usado em logos e sites para transmitir clareza e modernidade. Também é bom para layouts que buscam transmitir sensação de espaço e organização.",
                  "colorPalette": [
                    {"name": "Preto", "hex": "#000000"},
                    {"name": "Cinza Claro", "hex": "#CCCCCC"}
                  ]
                }
                """;
        ColorForm expectedColorForm = new ColorForm(
                "Branco Puro",
                "Representa pureza, limpeza, e simplicidade. É frequentemente associado à inocência, paz e perfeição. No design, pode transmitir minimalismo e modernidade.",
                new String[]{"Pureza", "Limpeza", "Minimalismo"},
                "Ideal para fundos, espaços negativos, e para destacar outros elementos. Usado em logos e sites para transmitir clareza e modernidade. Também é bom para layouts que buscam transmitir sensação de espaço e organização.",
                new SimpleColor[]{
                        new SimpleColor("Preto", "#000000"),
                        new SimpleColor("Cinza Claro", "#CCCCCC")
                }
        );

        when(objectMapper.readValue(geminiText, ColorForm.class)).thenReturn(expectedColorForm);

        ColorForm result = geminiService.responseToColorForm(geminiText);

        assertNotNull(result);
        assertEquals(expectedColorForm, result);
        verify(objectMapper).readValue(geminiText, ColorForm.class);
    }

    @Test
    void testResponseToColorForm_ErrorMapping() throws Exception {
        String invalidText = "invalid color form text";

        when(objectMapper.readValue(eq(invalidText), eq(ColorForm.class)))
                .thenThrow(new RuntimeException("Mapping error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                geminiService.responseToColorForm(invalidText)
        );

        assertTrue(thrown.getMessage().contains("Failed to map Gemini response to GeminiColorResponse."));
        verify(objectMapper).readValue(invalidText, ColorForm.class);
    }

    @Test
    void testColorFormToColorView() {
        ColorForm form = new ColorForm(
                "Branco Puro",
                "Representa pureza...",
                new String[]{"Pureza"},
                "Ideal para fundos...",
                new SimpleColor[]{new SimpleColor("Preto", "#000000")}
        );
        String hex = "#FFFFFF";

        Rgb rgb = new Rgb(255, 255, 255);
        List<String> psychologyTags = Arrays.asList(form.psychologyTags());
        List<SimpleColor> colorPalette = Arrays.asList(form.colorPalette());
        List<String> designUsageSuggestions = Arrays.asList(form.designUsageSuggestions().split("\n"));

        Color expectedColor = new Color(
                form.name(),
                hex,
                rgb,
                "0, 0%, 100%",
                1.0,
                false,
                true,
                form.description(),
                psychologyTags,
                colorPalette,
                "#000000",
                Arrays.asList("#E6E6E6", "#FFFFFF", "#FFFFFF"),
                "Primária (Branco)",
                designUsageSuggestions
        );
        ColorView expectedColorView = new ColorView(expectedColor);


        when(colorMapper.formToView(form, hex)).thenReturn(expectedColorView);

        ColorView result = ReflectionTestUtils.invokeMethod(geminiService, "colorFormToColorView", form, hex);

        assertNotNull(result);
        assertEquals(expectedColorView, result);
        verify(colorMapper).formToView(form, hex);
    }

    @Test
    void testValidatedSearchColor_InvalidHexadecimalException() {
        String invalidHex = "#GGGGGG";
        doThrow(new InvalidHexadecimalException("Invalid hexadecimal color code"))
                .when(colorService).validateHexColor(invalidHex.trim());

        ResponseEntity<?> response = geminiService.validatedSearchColor(invalidHex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid hexadecimal color code", response.getBody());
        verify(colorService).validateHexColor(invalidHex.trim());
        verifyNoInteractions(geminiClient, colorMapper, objectMapper);
    }
}
