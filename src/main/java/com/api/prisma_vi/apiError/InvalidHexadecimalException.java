package com.api.prisma_vi.apiError;

public class InvalidHexadecimalException extends RuntimeException {
    public InvalidHexadecimalException(String message) {
        super(message);
    }
}
