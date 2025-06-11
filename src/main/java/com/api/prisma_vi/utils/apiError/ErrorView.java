package com.api.prisma_vi.utils.apiError;

import org.springframework.http.HttpStatus;

public record ErrorView<T>(HttpStatus status, T message) {
}
