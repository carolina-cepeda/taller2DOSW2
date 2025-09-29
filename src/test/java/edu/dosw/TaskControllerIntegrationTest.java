package edu.dosw;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.controller.TaskController;
import edu.dosw.dto.TaskDTO;
import edu.dosw.model.States;
import edu.dosw.model.Task;
import edu.dosw.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@ContextConfiguration(classes = {TaskController.class})
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskDTO sampleTaskDTO() {
        return new TaskDTO(
                "123",
                "Task title",
                "Task desc",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                "PENDING"
        );
    }

    @Test
    void testCreateTask() throws Exception {
        TaskDTO dto = sampleTaskDTO();
        when(taskService.createTask(dto, "user1")).thenReturn(dto.toEntity());

        mockMvc.perform(post("/api/task/user1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task created"))
                .andExpect(jsonPath("$.data.title").value("Task title"))
                .andExpect(jsonPath("$.data.state").value("PENDING"));
    }

    @Test
    void testDeleteTask() throws Exception {
        when(taskService.deleteTask("user1", "task1")).thenReturn(true);

        mockMvc.perform(delete("/api/task/user1/tasks/task1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task deleted"))
                .andExpect(jsonPath("$.data").value(true));
    }
    @Test
    void testGetTasksByFilter() throws Exception {
        Task taskEntity = new Task();
        taskEntity.setId("1");
        taskEntity.setTitle("Test Task");
        taskEntity.setDescription("Desc");
        taskEntity.setState(States.PENDING);

        when(taskService.getTasks()).thenReturn(List.of(taskEntity));

        mockMvc.perform(get("/api/task/123/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Tasks"))
                .andExpect(jsonPath("$.data[0].title").value("Test Task"));

    }
    @Test
    void testGetTasksByFilter_success() throws Exception {
        Task task1 = new Task();
        task1.setId("1");
        task1.setTitle("Task A");
        task1.setDescription("Desc A");
        task1.setState(States.PENDING);
        task1.setDate(LocalDateTime.now());

        Task task2 = new Task();
        task2.setId("2");
        task2.setTitle("Task B");
        task2.setDescription("Desc B");
        task2.setState(States.PENDING);
        task2.setDate(LocalDateTime.now());

        when(taskService.getTasksByFilter("status", "PENDING"))
                .thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/api/task/123/tasks/status/PENDING")) // OJO: ruta correcta
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Tasks"))
                .andExpect(jsonPath("$.data[0].title").value("Task A"))
                .andExpect(jsonPath("$.data[1].title").value("Task B"));

        verify(taskService).getTasksByFilter("status", "PENDING");
    }

    @Test
    void testCreateTask_nullBodyReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/task/123/tasks")
                        .contentType(MediaType.APPLICATION_JSON)) // sin body
                .andExpect(status().isBadRequest());
    }

}
