package com.api.prisma_vi.gemini;

import com.api.prisma_vi.apiError.InvalidHexadecimalException;
import com.api.prisma_vi.colors.ColorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search-color")
public class GeminiController {

    private final GeminiService geminiService;

    @Autowired
    private ColorsService colorsService;

    @Autowired
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public ResponseEntity<?> generate(@RequestBody String hex) {
        try{colorsService.validateHexColor(hex);}
        catch (InvalidHexadecimalException e){
         return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(
                geminiService.formatResponse(
                        geminiService.generateContent(
                                geminiService.generatePrompt(hex.trim())))
        );
    }
}
