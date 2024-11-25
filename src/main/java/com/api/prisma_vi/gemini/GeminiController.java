package com.api.prisma_vi.gemini;

import com.api.prisma_vi.apiError.InvalidHexadecimalException;
import com.api.prisma_vi.colors.ColorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GeminiController {

    private final GeminiService geminiService;

    @Autowired
    private ColorsService colorsService;

    @Autowired
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/mock/search-color")
    public ResponseEntity<?> mockSearchColor(@RequestBody String hex){
        try{colorsService.validateHexColor(hex);}
        catch (InvalidHexadecimalException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        String jsonResponse = "{\n" +
                "\"Color\": {\n" +
                "\"name\": \"Deep Forest Green\",\n" +
                "\"hexCode\": \""+hex+"\",\n" +
                "\"rgbCode\": \"3, 10, 0\",\n" +
                "\"rybPercentages\": {\n" +
                "\"r\": \"0%\",\n" +
                "\"y\": \"20%\",\n" +
                "\"b\": \"80%\"\n" +
                "},\n" +
                "\"colorTemperature\": \"cool\",\n" +
                "\"colorDescription\": \"This color evokes feelings of peace, tranquility, and nature.  It's reminiscent of deep forests and lush vegetation.\",\n" +
                "\"twoHexOfColorsThatMatch\": [\n" +
                "\"#001f00\",\n" +
                "\"#002500\"\n" +
                "],\n" +
                "\"colorTerminology\": \"tertiary\"\n" +
                "}\n" +
                "}";

        return ResponseEntity.ok(geminiService.responseToColorView(jsonResponse));
    }

    @PostMapping("/search-color")
    public ResponseEntity<?> generate(@RequestBody String hex) {
        try{colorsService.validateHexColor(hex);}
        catch (InvalidHexadecimalException e){
         return ResponseEntity.badRequest().body(e.getMessage());
        }


        return ResponseEntity.ok().body(
                geminiService.responseToColorView(
                        geminiService.formatResponse(
                                geminiService.generateContent(
                                        geminiService.generatePrompt(hex.trim()))))
                );
    }
}
