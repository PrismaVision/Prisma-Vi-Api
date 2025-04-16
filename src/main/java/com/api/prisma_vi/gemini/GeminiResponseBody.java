package com.api.prisma_vi.gemini;

import java.util.List;

public record GeminiResponseBody(
        List<Candidate> candidates,
        UsageMetadata usageMetadata,
        String modelVersion
) {}

record Candidate(
        Content content,
        String finishReason,
        double avgLogprobs) {}

record Content(
        List<Part> parts,
        String role
) {}

record Part(
        String text
) {}

record UsageMetadata(
        int promptTokenCount,
        int candidatesTokenCount,
        int totalTokenCount,
        List<TokenDetails> promptTokensDetails,
        List<TokenDetails> candidatesTokensDetails
) {}

record TokenDetails(
        String modality,
        int tokenCount
) {}