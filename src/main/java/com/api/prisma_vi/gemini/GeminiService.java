package com.api.prisma_vi.gemini;

import com.api.prisma_vi.colors.ColorResponseWrapper;
import com.api.prisma_vi.colors.ColorsForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GeminiService {

    private final GeminiClient geminiClient;

    @Value("${gemini.api.token}")
    private String apiToken;

    @Autowired
    public GeminiService(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    public String generatePrompt(String hex){

        String[] languages = {"pt-br","en-eu"};

        ColorsForm object = new ColorsForm(
                "(the name of the closest common color)",
                "(HEX code of color)",
                "(RGB code of color)",
                "(red yellow and blue percentages to make the color with this format: {r: x%, y: x%, b: x%})",
                "(hot, cold or neutral)",
                "(a brief description of the color [color name], including its visual characteristics, how it is formed and what it conveys in terms of feelings, environments or objects that represent it, as well as examples of where this color can be found in nature or in the everyday)",
                "(two colors that match with the main color in HEX code)",
                "(primary, secondary, tertiary, neutral or terrestrial)"
        );

        String order = "considering the color: "
                + hex
                + " fill the object by replacing the values in parentheses according to what the values in parentheses and the require:\n"
                + object.toString()
                + "\nand translate the values to: "
                + languages[1];

        return order;
    }


    public String generateContent(String prompt) {
        var part = new GeminiRequestBody.Part(prompt);
        var content = new GeminiRequestBody.Content(Collections.singletonList(part));
        var generationConfig = new GeminiRequestBody.GenerationConfig("application/json");
        var geminiRequestBody = new GeminiRequestBody(
                Collections.singletonList(content),
                generationConfig
        );
        return geminiClient.searchColor(geminiRequestBody, apiToken).getBody();
    }

    public String formatResponse(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            GeminiResponseBody response = objectMapper.readValue(jsonResponse, GeminiResponseBody.class);
            return response.candidates().get(0).content().parts().get(0).text();
        } catch (Exception e) {
            return e.toString();
        }
    }
    public ColorResponseWrapper responseToColorView(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, ColorResponseWrapper.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

