package com.example.todoedu.dto

data class ToDoRequest(
    val nameTask: String,
    val description: String,
)

data class ToDoResponse(
    val id: Int,
    val nameTask: String,
    val description: String,
)
