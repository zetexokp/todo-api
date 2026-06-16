package org.example.todo_api.service;

import lombok.RequiredArgsConstructor;
import org.example.todo_api.dto.TaskRequest;
import org.example.todo_api.dto.TaskResponse;
import org.example.todo_api.entity.Task;
import org.example.todo_api.mapper.TaskMapper;
import org.example.todo_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    // Создание
    public TaskResponse create(TaskRequest request) {
        Task task = mapper.toEntity(request);
        return mapper.toResponse(repository.save(task));
    }

    // Получение одной задачи по ID
    public TaskResponse getById(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача с ID " + id + " не найдена"));
        return mapper.toResponse(task);
    }

    // Удаление
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Невозможно удалить: задача не найдена");
        }
        repository.deleteById(id);
    }

    // Получение всех задач
    public List<TaskResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse) // Преобразуем каждую Entity в DTO
                .toList();
    }

    public TaskResponse update(Long id, TaskRequest request) {

        Task task = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Задача с ID " + id + " не найдена"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        Task updatedTask = repository.save(task);

        return mapper.toResponse(updatedTask);
    }

    public TaskResponse complete(Long id) {

        Task task = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Задача с ID " + id + " не найдена"));

        task.setCompleted(true);

        return mapper.toResponse(repository.save(task));
    }
}