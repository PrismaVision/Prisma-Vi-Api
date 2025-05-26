package com.api.prisma_vi.gemini;

import com.api.prisma_vi.color.Color;
import com.api.prisma_vi.color.ColorView;
import com.api.prisma_vi.color.Rgb;
import com.api.prisma_vi.color.SimpleColor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mock")
public class GeminiMockController {

    @PostMapping("/search-color")
    public ResponseEntity<ColorView> searchColor(@RequestBody String hex) {

        ColorView colorView = new ColorView(
                new Color(
                        "Verde Floresta Escuro",
                        "#001f00",
                        new Rgb(0, 31, 0),
                        "120, 100%, 6%",
                        0.009799729795421583,
                        true,
                        false,
                        "Evoca sensações de natureza, tranquilidade e mistério. Pode sugerir crescimento, renovação e uma ligação com o ambiente natural.",
                        List.of("Tranquilidade", "Equilíbrio", "Natureza"),
                        List.of(
                                new SimpleColor("Verde Lima", "#32CD32"),
                                new SimpleColor("Verde Musgo", "#4A5C4C")
                        ),
                        "#1F001F",
                        List.of("#000000", "#005200", "#008500"),
                        "Secundária (Verde)",
                        List.of(
                                "Ideal para marcas que desejam transmitir sustentabilidade, saúde ou produtos orgânicos. Bom para interfaces que buscam um visual calmo e relaxante, como aplicativos de bem-estar."
                        )
                )
        );
        return ResponseEntity.ok(colorView);
    }
}
