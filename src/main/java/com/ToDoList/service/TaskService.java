package com.ToDoList.service;

import com.ToDoList.entity.Task;

import java.util.List;

public interface TaskService {
    Task addTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(Long id);
    Task updateTask(Long id, Task updatedTask);
    void deleteTask(Long id);

}

