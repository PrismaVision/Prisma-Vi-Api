package com.api.prisma_vi.utils.apiError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HttpErrorHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorView<List<ErrorFieldView>>> treatCode400(MethodArgumentNotValidException ex) {
        List<ErrorFieldView> errorFields = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorFieldView(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorView<List<ErrorFieldView>> errorView = new ErrorView<>(HttpStatus.NOT_ACCEPTABLE, errorFields);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorView);
    }

}
