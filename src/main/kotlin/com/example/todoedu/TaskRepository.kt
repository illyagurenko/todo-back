package com.example.todoedu
import org.springframework.data.repository.CrudRepository

interface TaskRepository: CrudRepository<ToDoData, Int> {
}