package edu.dosw.controller;

import edu.dosw.dto.TaskDTO;
import edu.dosw.model.Task;
import edu.dosw.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private TaskService taskService;
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class);
        taskController = new TaskController(taskService);
    }

    private TaskDTO sampleTaskDTO() {
        return new TaskDTO("1", "Test task", "desc", LocalDateTime.of(2025, 1, 1, 12, 0), "PENDING");
    }

    @Test
    void test_createTask_should_return_badRequest_when_task_is_null() {
        // Act
        ResponseEntity<?> response = taskController.createTask("user1", null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Task is null"));
        verify(taskService, never()).createTask(any(), anyString());
    }

    @Test
    void test_createTask_should_return_ok_when_task_is_valid() {
        // Arrange
        TaskDTO dto = sampleTaskDTO();
        Task mockTask = dto.toEntity();

        when(taskService.createTask(dto, "user1")).thenReturn(mockTask);

        // Act
        ResponseEntity<?> response = taskController.createTask("user1", dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Task created"));
        verify(taskService).createTask(dto, "user1");
    }

    @Test
    void test_getTasks_should_return_badRequest_when_userId_is_null() {
        // Act
        ResponseEntity<?> response = taskController.getTasks(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("User id is null"));
        verify(taskService, never()).getTasks();
    }

    @Test
    void test_getTasks_should_return_ok_with_task_list() {
        // Arrange
        Task task = new Task();
        task.setId("1");
        task.setTitle("Test task");

        when(taskService.getTasks()).thenReturn(List.of(task));

        // Act
        ResponseEntity<?> response = taskController.getTasks("user1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Tasks"));
        verify(taskService).getTasks();
    }

    @Test
    void test_getTasksByFilter_should_return_badRequest_when_filter_or_extra_is_null() {
        // Act
        ResponseEntity<?> response1 = taskController.getTasksByFilter("user1", null, "extra");
        ResponseEntity<?> response2 = taskController.getTasksByFilter("user1", "filter", null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        verify(taskService, never()).getTasksByFilter(anyString(), anyString());
    }

    @Test
    void test_getTasksByFilter_should_return_ok_with_filtered_tasks() {
        // Arrange
        Task task = new Task();
        task.setId("1");
        task.setTitle("Filtered task");

        when(taskService.getTasksByFilter("keyword", "Test")).thenReturn(List.of(task));

        // Act
        ResponseEntity<?> response = taskController.getTasksByFilter("user1", "keyword", "Test");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Tasks"));
        verify(taskService).getTasksByFilter("keyword", "Test");
    }

    @Test
    void test_deleteTask_should_return_badRequest_when_id_is_null() {
        // Act
        ResponseEntity<?> response = taskController.deleteTask("user1", null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Task id is null"));
        verify(taskService, never()).deleteTask(anyString(), anyString());
    }

    @Test
    void test_deleteTask_should_return_ok_when_task_deleted() {
        // Arrange
        when(taskService.deleteTask("user1", "task1")).thenReturn(true);

        // Act
        ResponseEntity<?> response = taskController.deleteTask("user1", "task1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Task deleted"));
        verify(taskService).deleteTask("user1", "task1");
    }
}
