package com.api.prisma_vi.color;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SimpleColor(
        @JsonProperty String name,
        @JsonProperty String hex 
){}
