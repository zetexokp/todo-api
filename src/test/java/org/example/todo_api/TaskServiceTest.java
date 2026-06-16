package org.example.todo_api;

import org.example.todo_api.dto.TaskRequest;
import org.example.todo_api.dto.TaskResponse;
import org.example.todo_api.entity.Task;
import org.example.todo_api.mapper.TaskMapper;
import org.example.todo_api.repository.TaskRepository;
import org.example.todo_api.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskService service;

    @Test
    void shouldCreateTask() {

        TaskRequest request = new TaskRequest();
        request.setTitle("Learn Spring");
        request.setDescription("Practice CRUD");

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle(request.getTitle());
        savedTask.setDescription(request.getDescription());

        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("Learn Spring");

        when(mapper.toEntity(request)).thenReturn(task);
        when(repository.save(task)).thenReturn(savedTask);
        when(mapper.toResponse(savedTask)).thenReturn(response);

        TaskResponse result = service.create(request);

        assertEquals(1L, result.getId());
        assertEquals("Learn Spring", result.getTitle());

        verify(repository).save(task);
    }
    @Test
    void shouldReturnTaskById() {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task");

        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("Task");

        when(repository.findById(1L))
                .thenReturn(java.util.Optional.of(task));

        when(mapper.toResponse(task))
                .thenReturn(response);

        TaskResponse result = service.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Task", result.getTitle());
    }
    @Test
    void shouldThrowExceptionWhenTaskNotFound() {

        when(repository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.getById(999L)
        );

        assertTrue(exception.getMessage().contains("не найдена"));
    }
    @Test
    void shouldDeleteTask() {

        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }
    @Test
    void shouldUpdateTask() {

        TaskRequest request = new TaskRequest();
        request.setTitle("Updated");
        request.setDescription("Updated desc");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Old");

        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("Updated");

        when(repository.findById(1L))
                .thenReturn(java.util.Optional.of(task));

        when(repository.save(task))
                .thenReturn(task);

        when(mapper.toResponse(task))
                .thenReturn(response);

        TaskResponse result = service.update(1L, request);

        assertEquals("Updated", result.getTitle());

        verify(repository).save(task);
    }
}