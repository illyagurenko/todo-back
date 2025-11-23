package com.example.todoedu

import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ToDoService(private val taskRepository: TaskRepository) {

    fun findAll(): Iterable<ToDoData> = taskRepository.findAll()

    fun findById(id: Int): Optional<ToDoData> = taskRepository.findById(id)

    fun save(task: ToDoData): ToDoData = taskRepository.save(task)

    fun deleteById(id: Int) = taskRepository.deleteById(id)

    fun update(id: Int, task: ToDoData): Optional<ToDoData>{
        return if(taskRepository.existsById(id)) {
            task.id = id
            val savedTask = taskRepository.save(task)
            Optional.of(savedTask)
        }
        else {
            Optional.empty()
        }
    }
}