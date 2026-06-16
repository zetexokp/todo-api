package org.example.todo_api.dto;

import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}