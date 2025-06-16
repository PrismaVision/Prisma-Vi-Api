package com.api.prisma_vi.color;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Rgb(
        @JsonProperty int red,
        @JsonProperty int green,
        @JsonProperty int blue
){}
