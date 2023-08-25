package com.ToDoList.data;

import com.ToDoList.entity.Task;
import com.ToDoList.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final TaskService taskService;

    public DataInitializer(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) {
        Task task1 = new Task();
        task1.setTitle("Complete Assignment 1");
        task1.setDescription("Finish assignment on Spring Boot");

        Task task2 = new Task();
        task2.setTitle("Java Developer");
        task2.setDescription("Java is Secure programming Language");

        taskService.addTask(task1);
        taskService.addTask(task2);
    }
}
