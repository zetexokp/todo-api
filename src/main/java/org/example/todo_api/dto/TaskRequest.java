package org.example.todo_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // Генерирует геттеры, сеттеры, equals, hashcode
public class TaskRequest {
    @NotBlank(message = "Название не может быть пустым")
    @Size(min = 3, max = 50, message = "Название от 3 до 50 символов")
    private String title;

    private String description;
}