package com.api.prisma_vi.gemini;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geminiClient", url = "https://generativelanguage.googleapis.com")
public interface GeminiClient {


    @PostMapping("/v1beta/models/gemini-1.5-flash-8b:generateContent")
    ResponseEntity<String> searchColor(@RequestBody GeminiRequestBody body,
                                       @RequestParam("key") String apiKey);


}
