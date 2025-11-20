package com.example.todoedu
import org.springframework.stereotype.Component
import jakarta.annotation.PostConstruct

@Component
class DataLoader {
    private val taskRepository: TaskRepository
    constructor(taskRepository: TaskRepository){
        this.taskRepository = taskRepository
    }
    @PostConstruct
    private fun loadData(){
        taskRepository.saveAll(listOf(
            ToDoData(nameTask="math"),
            ToDoData(nameTask="physics"),
            ToDoData(nameTask="spring"),
            ToDoData(nameTask="kotlin"),
            ToDoData(nameTask="drill")
        ))
    }
}