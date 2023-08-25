package com.ToDoList.Exception;

public class InvalidTaskInputException extends RuntimeException {
    public InvalidTaskInputException(String message) {
        super(message);
    }
}

