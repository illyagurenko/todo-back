package com.example.todoedu
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
class ToDoController(private val taskRepository: TaskRepository) {
    @GetMapping
    fun getAllTasks(): Iterable<ToDoData>{
        return taskRepository.findAll()
    }
    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Int): Optional<ToDoData>{
        return taskRepository.findById(id)
    }
    @PostMapping
    fun setTask(@RequestBody task: ToDoData): ToDoData?{
        return taskRepository.save(task)
    }
    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Int, @RequestBody task: ToDoData): ResponseEntity<ToDoData>{
        return if(taskRepository.existsById(id)){
            task.id = id
            val savedTask = taskRepository.save(task)
            ResponseEntity(savedTask, HttpStatus.OK)
        }
        else ResponseEntity<ToDoData>( HttpStatus.NOT_FOUND)
    }
    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Int){
        return taskRepository.deleteById(id)
    }

}