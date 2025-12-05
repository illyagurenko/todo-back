package com.example.todoedu.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ToDoRequest(
    @field:NotBlank(message = "Название задачи не может быть пустым")
    val nameTask: String,
    @field:Size(max = 150, message = "описание слишком длинное")
    val description: String,
    val isCompleted: Boolean = false,
)

data class ToDoResponse(
    val id: Int,
    val nameTask: String,
    val description: String,
    val isCompleted: Boolean,
)
