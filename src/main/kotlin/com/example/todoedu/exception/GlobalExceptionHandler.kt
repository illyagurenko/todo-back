package com.example.todoedu.exception

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.MethodArgumentNotValidException

@RestControllerAdvice
@Hidden
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validationError(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>>{
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.fieldErrors.forEach { error ->
            errors[error.field] = error.defaultMessage ?: "Ошибка валидации"
        }

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}