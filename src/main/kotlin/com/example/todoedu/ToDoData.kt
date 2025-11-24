package com.example.todoedu

import com.example.todoedu.dto.ToDoResponse
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType



@Entity
data class ToDoData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var nameTask: String = "",
    var description: String = ""
)

fun ToDoData.toResponse(): ToDoResponse = ToDoResponse(
    id = this.id!!,
    nameTask = this.nameTask,
    description = this.description
)