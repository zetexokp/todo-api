package org.example.todo_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.todo_api.dto.TaskResponse;
import org.example.todo_api.entity.Task;
import org.example.todo_api.service.TaskService;
import org.example.todo_api.dto.TaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Операции с задачами")
public class TaskController {

    private final TaskService service;

    @Operation(summary = "Создать задачу")
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "Получить задачу по ID")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Удалить задачу")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить все задачи")
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Обновить задачу")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Удалить задачу")
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Long id) {
        return ResponseEntity.ok(service.complete(id));
    }
}