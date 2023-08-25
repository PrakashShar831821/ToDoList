package com.ToDoList;

import com.ToDoList.controller.TaskController;
import com.ToDoList.entity.Task;
import com.ToDoList.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskController taskController;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;
    @MockBean
    private BindingResult bindingResult;

    @Test
    public void testGetAllTasks() throws Exception {
        Task task1 = new Task(1L, "Task 1", "Description 1", false);
        Task task2 = new Task(2L, "Task 2", "Description 2", true);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].completed").value(true));
    }

    @Test
    public void testAddTaskWithValidData() {
        Task task=new Task();
        when(taskService.addTask(task)).thenReturn(task);


        ResponseEntity<?> response = taskController.addTask(task,bindingResult);

        verify(taskService).addTask(task);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    public void testGetTaskById() throws Exception {
        Task task = new Task(1L, "Task 1", "Description 1", false);

        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.completed").value(false));
    }
    @Test
    public void testUpdateTask() throws Exception {
        // Test data setup
        Task existingTask = new Task(1L, "Task 1", "Description 1", false);
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", true);

        // Mocking the service responses
        when(taskService.getTaskById(1L)).thenReturn(existingTask);
        when(taskService.updateTask(any(Long.class), any(Task.class))).thenReturn(updatedTask);

        // Performing the actual test
        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }

    // More test methods...

}
