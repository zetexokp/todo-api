package org.example.todo_api.mapper;

import org.example.todo_api.dto.TaskRequest;
import org.example.todo_api.dto.TaskResponse;
import org.example.todo_api.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toEntity(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(false);
        return task;
    }

    public TaskResponse toResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setCompleted(task.isCompleted());
        return response;
    }
}