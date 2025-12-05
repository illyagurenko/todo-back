package com.example.todoedu

import com.example.todoedu.dto.ToDoRequest
import com.example.todoedu.dto.ToDoResponse
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.collections.map

@Service
class ToDoService(private val taskRepository: TaskRepository) {

    fun findAll(): Iterable<ToDoResponse> = taskRepository.findAll().map { it.toResponse() }

    fun findById(id: Int): Optional<ToDoResponse> = taskRepository.findById(id).map { it.toResponse() }

    fun save(request: ToDoRequest): ToDoResponse{
        val entity = ToDoData(nameTask = request.nameTask, description = request.description, isCompleted = request.isCompleted)
        return taskRepository.save(entity).toResponse()
    }
    fun deleteById(id: Int) = taskRepository.deleteById(id)

    fun update(id: Int, request: ToDoRequest): Optional<ToDoResponse> {
        return if (taskRepository.existsById(id)) {
            val entity = ToDoData(
                id = id,
                nameTask = request.nameTask,
                description = request.description,
                isCompleted = request.isCompleted
            )
            Optional.of(taskRepository.save(entity).toResponse())
        } else {
            Optional.empty()
        }
    }

    fun updatePartial(id: Int, updates: Map<String, Any>): Optional<ToDoResponse> {
        if (!taskRepository.existsById(id)) return Optional.empty()

        val entity = taskRepository.findById(id).get()

        // Проверяем, прислали ли нам статус?
        if (updates.containsKey("isCompleted")) {
            // Важно: JSON всегда присылает boolean, Kotlin это поймет
            entity.isCompleted = updates["isCompleted"] as Boolean
        }

        // Заодно проверим, не прислали ли другие поля (на будущее)
        if (updates.containsKey("nameTask")) {
            entity.nameTask = updates["nameTask"] as String
        }
        if (updates.containsKey("description")) {
            entity.description = updates["description"] as String
        }

        val saved = taskRepository.save(entity)
        return Optional.of(saved.toResponse())

    }
}