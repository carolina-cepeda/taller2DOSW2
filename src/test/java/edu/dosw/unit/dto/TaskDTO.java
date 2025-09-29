package edu.dosw.unit.dto;

import edu.dosw.dto.TaskDTO;
import edu.dosw.model.States;
import edu.dosw.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskDTOTest {

    @Test
    void testDefaultsWhenNullValues() {
        TaskDTO dto = new TaskDTO("1", "Title", "Description", null, null);

        assertNotNull(dto.date(), "Date should default to now");
        assertEquals(States.PENDING.name(), dto.state(), "State should default to PENDING");
    }

    @Test
    void testFromTask() {
        LocalDateTime now = LocalDateTime.now();
        Task task = new Task("My Task", "Some description", now, States.IN_PROGRESS);
        task.setId("123");

        TaskDTO dto = TaskDTO.fromTask(task);

        assertEquals("123", dto.id());
        assertEquals("My Task", dto.title());
        assertEquals("Some description", dto.description());
        assertEquals(now, dto.date());
        assertEquals(States.IN_PROGRESS.name(), dto.state());
    }

    @Test
    void testToEntity() {
        LocalDateTime now = LocalDateTime.now();
        TaskDTO dto = new TaskDTO("456", "Task DTO", "DTO description", now, "COMPLETE");

        Task task = dto.toEntity();

        assertEquals("456", task.getId());
        assertEquals("Task DTO", task.getTitle());
        assertEquals("DTO description", task.getDescription());
        assertEquals(now, task.getDate());
        assertEquals(States.COMPLETE, task.getState());
    }

    @Test
    void testToEntityGeneratesIdWhenNull() {
        TaskDTO dto = new TaskDTO(null, "Generated ID Task", "desc", LocalDateTime.now(), "PENDING");

        Task task = dto.toEntity();

        assertNotNull(task.getId(), "Task ID should be generated when null");
    }
}
