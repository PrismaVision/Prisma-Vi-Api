package com.api.prisma_vi.gemini.feign;

import com.api.prisma_vi.gemini.GeminiRequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geminiClient", url = "https://generativelanguage.googleapis.com", configuration = FeignConfig.class)
public interface GeminiClient {


    @PostMapping("/v1beta/models/gemini-2.0-flash-lite:generateContent")
    ResponseEntity<String> searchColor(@RequestBody GeminiRequestBody body,
                                       @RequestParam("key") String apiKey);


}
