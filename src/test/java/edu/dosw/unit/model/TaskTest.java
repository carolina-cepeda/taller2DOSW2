package edu.dosw.unit.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import edu.dosw.model.States;
import edu.dosw.model.Task;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void testConstructorWithSetters() {
        Task task = new Task();

        String id = "123";
        String title = "title";
        String description = "Description";
        LocalDateTime date = LocalDateTime.now();
        States state = States.IN_PROGRESS;

        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setDate(date);
        task.setState(state);

        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(date, task.getDate());
        assertEquals(state, task.getState());
    }

    @Test
    void testConstructorWithParameters() {
        String title = "Title";
        String description = "Description2";
        LocalDateTime date = LocalDateTime.now();
        States state = States.PENDING;

        Task task = new Task(title, description, date, state);

        assertNull(task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(date, task.getDate());
        assertEquals(state, task.getState());
    }

    @Test
    void testUpdateFields() {
        Task task = new Task("inicial test", "Init Description", LocalDateTime.now(), States.PENDING);

        task.setTitle("Updated Title");
        task.setDescription("Updated Description");
        task.setState(States.COMPLETE);

        assertEquals("Updated Title", task.getTitle());
        assertEquals("Updated Description", task.getDescription());
        assertEquals(States.COMPLETE, task.getState());
    }

    @Test
    void testStatesEnumValues() {
        States[] values = States.values();

        assertEquals(3, values.length);
        assertTrue(java.util.Arrays.asList(values).contains(States.PENDING));
        assertTrue(java.util.Arrays.asList(values).contains(States.IN_PROGRESS));
        assertTrue(java.util.Arrays.asList(values).contains(States.COMPLETE));
    }
}
