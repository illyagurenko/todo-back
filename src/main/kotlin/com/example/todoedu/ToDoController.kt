package com.example.todoedu
import com.example.todoedu.dto.ToDoRequest
import com.example.todoedu.dto.ToDoResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import java.util.Optional

@RestController
@RequestMapping("/ToDoTask")
class ToDoController(private val toDoService: ToDoService) {
    @GetMapping
    fun getAllTasks(): Iterable<ToDoResponse>{
        return toDoService.findAll()
    }
    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Int): ResponseEntity<ToDoResponse> {
        val task = toDoService.findById(id)
        return if (task.isPresent) ResponseEntity.ok(task.get())
        else ResponseEntity.notFound().build()
    }
    @PostMapping
    fun setTask(@Valid @RequestBody request: ToDoRequest): ToDoResponse?{
        return toDoService.save(request)
    }
    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Int, @Valid @RequestBody request: ToDoRequest): ResponseEntity<ToDoResponse> {
        val updated = toDoService.update(id, request)
        return if (updated.isPresent) ResponseEntity.ok(updated.get())
        else ResponseEntity.notFound().build()
    }
    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Int){
        return toDoService.deleteById(id)
    }

}