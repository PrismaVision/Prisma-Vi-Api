package com.api.prisma_vi.apiError

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class HttpErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun treatCode400(ex: MethodArgumentNotValidException): ResponseEntity<ErrorView<List<ErrorFieldView>>> =
        ResponseEntity.status(406).body(
            ErrorView(
                HttpStatus.NOT_ACCEPTABLE,
                ex.fieldErrors.map { ErrorFieldView(it.field, it.defaultMessage) })
        )

}