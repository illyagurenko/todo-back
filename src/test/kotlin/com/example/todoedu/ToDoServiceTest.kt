package com.example.todoedu
import com.example.todoedu.dto.ToDoRequest
import com.example.todoedu.ToDoData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import org.junit.jupiter.api.Assertions.*


@ExtendWith(MockitoExtension::class)
class ToDoServiceTest {
   @Mock
   lateinit var taskRepository: TaskRepository

   @InjectMocks
   lateinit var toDoService: ToDoService

   @Test
   fun `findAll should return list of responses`(){
       val fakeEntity = ToDoData(1, "Test", "description")
       Mockito.`when`(taskRepository.findAll()).thenReturn(listOf(fakeEntity))


       val result = toDoService.findAll().toList()


       assertEquals(1, result.size)
       assertEquals("Test", result[0].nameTask)
   }

}