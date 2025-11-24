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
        val entity = ToDoData(nameTask = request.nameTask, description = request.description)
        return taskRepository.save(entity).toResponse()
    }
    fun deleteById(id: Int) = taskRepository.deleteById(id)

    fun update(id: Int, request: ToDoRequest): Optional<ToDoResponse>{
        return if(taskRepository.existsById(id)) {
            val entity = ToDoData(id = id, nameTask = request.nameTask, description = request.description)
            Optional.of(taskRepository.save(entity).toResponse())
        }
        else {
            Optional.empty()
        }
    }
}