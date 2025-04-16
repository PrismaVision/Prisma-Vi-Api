package com.api.prisma_vi.gemini;

import com.api.prisma_vi.utils.apiError.InvalidHexadecimalException;
import com.api.prisma_vi.color.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService, ColorService colorService) {
        this.geminiService = geminiService;
    }


    @PostMapping("/search-color")
    public ResponseEntity<?> searchColor(@RequestBody String hex) {
        return geminiService.validatedSearchColor(hex);
    }
}
