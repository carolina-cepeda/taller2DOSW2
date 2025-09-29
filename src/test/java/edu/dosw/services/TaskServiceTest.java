package edu.dosw.services;

import edu.dosw.dto.TaskDTO;
import edu.dosw.model.Task;
import edu.dosw.model.User;
import edu.dosw.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private UserService userService;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        userService = mock(UserService.class);
        taskService = new TaskService(taskRepository, userService);
    }

    @Test
    void test_TaskService_should_return_all_tasks() {
        // Arrange
        List<Task> tasks = List.of(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<Task> result = taskService.getTasks();

        // Assert
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void test_TaskService_should_filter_tasks_by_keyword() {
        // Arrange
        Task t1 = new Task();
        t1.setTitle("homework");
        Task t2 = new Task();
        t2.setTitle("shopping");
        when(taskRepository.findAll()).thenReturn(List.of(t1, t2));

        // Act
        List<Task> result = taskService.getTasksByFilter("keyword", "shop");

        // Assert
        assertEquals(1, result.size());
        assertEquals("shopping", result.get(0).getTitle());
    }


    @Test
    void test_TaskService_should_create_task_when_user_is_authorized() {
        // Arrange
        User user = mock(User.class);
        when(user.canCreateTask()).thenReturn(true);
        when(userService.getUserById("u1")).thenReturn(user);

        TaskDTO dto = mock(TaskDTO.class);
        Task entity = new Task();
        when(dto.toEntity()).thenReturn(entity);
        when(taskRepository.save(entity)).thenReturn(entity);

        // Act
        Task result = taskService.createTask(dto, "u1");

        // Assert
        assertEquals(entity, result);
        verify(taskRepository).save(entity);
    }

    @Test
    void test_TaskService_should_throw_when_user_cannot_create_task() {
        // Arrange
        User user = mock(User.class);
        when(user.canCreateTask()).thenReturn(false);
        when(userService.getUserById("u1")).thenReturn(user);
        TaskDTO dto = mock(TaskDTO.class);

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> taskService.createTask(dto, "u1"));
        assertEquals("User is not authorized to create tasks", ex.getMessage());
    }

    @Test
    void test_TaskService_should_update_task_when_user_is_authorized() {
        // Arrange
        User user = mock(User.class);
        when(user.canUpdateTask()).thenReturn(true);
        when(userService.getUserById("u1")).thenReturn(user);

        TaskDTO dto = mock(TaskDTO.class);
        Task entity = new Task();
        when(dto.toEntity()).thenReturn(entity);
        when(taskRepository.save(entity)).thenReturn(entity);

        // Act
        Task result = taskService.updateTask(dto, "u1");

        // Assert
        assertEquals(entity, result);
        verify(taskRepository).save(entity);
    }

    @Test
    void test_TaskService_should_throw_when_user_cannot_update_task() {
        // Arrange
        User user = mock(User.class);
        when(user.canUpdateTask()).thenReturn(false);
        when(userService.getUserById("u1")).thenReturn(user);
        TaskDTO dto = mock(TaskDTO.class);

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> taskService.updateTask(dto, "u1"));
        assertEquals("User is not authorized to update tasks", ex.getMessage());
    }

    @Test
    void test_TaskService_should_delete_task_when_user_is_authorized() {
        // Arrange
        User user = mock(User.class);
        when(user.canDeleteTasks()).thenReturn(true);
        when(userService.getUserById("u1")).thenReturn(user);

        when(taskRepository.findById("t1")).thenReturn(Optional.empty());

        // Act
        boolean result = taskService.deleteTask("u1", "t1");

        // Assert
        assertTrue(result);
        verify(taskRepository).deleteById("t1");
        verify(taskRepository).findById("t1");
    }

    @Test
    void test_TaskService_should_throw_when_user_cannot_delete_task() {
        // Arrange
        User user = mock(User.class);
        when(user.canDeleteTasks()).thenReturn(false);
        when(userService.getUserById("u1")).thenReturn(user);

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> taskService.deleteTask("u1", "t1"));
        assertEquals("User is not authorized to delete tasks", ex.getMessage());
    }
}
