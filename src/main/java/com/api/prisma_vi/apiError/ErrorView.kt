package com.api.prisma_vi.apiError

import org.springframework.http.HttpStatus

data class ErrorView<T>(val status: HttpStatus, val message: Any?)
