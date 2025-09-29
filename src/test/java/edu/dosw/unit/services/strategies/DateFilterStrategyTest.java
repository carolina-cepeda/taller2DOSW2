package edu.dosw.unit.services.strategies;

import edu.dosw.model.Task;
import edu.dosw.services.strategies.DateFilterStrategy;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DateFilterStrategyTest {

    private final DateFilterStrategy strategy = new DateFilterStrategy();

    private Task buildTask(String id, LocalDateTime date) {
        Task task = new Task();
        task.setId(id);
        task.setDate(date);
        return task;
    }

    @Test
    void testFilter_shouldSortTasksByDateAscending() {
        // Arrange
        Task t1 = buildTask("1", LocalDateTime.of(2025, 1, 3, 10, 0));
        Task t2 = buildTask("2", LocalDateTime.of(2025, 1, 1, 10, 0));
        Task t3 = buildTask("3", LocalDateTime.of(2025, 1, 2, 10, 0));

        List<Task> tasks = new ArrayList<>(List.of(t1, t2, t3));

        // Act
        List<Task> result = strategy.filter(tasks, null);

        // Assert
        assertThat(result)
                .extracting(Task::getId)
                .containsExactly("2", "3", "1"); // orden esperado
    }

    @Test
    void testFilter_shouldKeepOrderWhenAlreadySorted() {
        // Arrange
        Task t1 = buildTask("1", LocalDateTime.of(2025, 1, 1, 10, 0));
        Task t2 = buildTask("2", LocalDateTime.of(2025, 1, 2, 10, 0));
        List<Task> tasks = new ArrayList<>(List.of(t1, t2));

        // Act
        List<Task> result = strategy.filter(tasks, null);

        // Assert
        assertThat(result)
                .extracting(Task::getId)
                .containsExactly("1", "2");
    }

    @Test
    void testFilter_shouldHandleEmptyList() {
        // Arrange
        List<Task> tasks = new ArrayList<>();

        // Act
        List<Task> result = strategy.filter(tasks, null);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void testFilter_shouldKeepRelativeOrderForSameDate() {
        // Arrange
        LocalDateTime sameDate = LocalDateTime.of(2025, 1, 1, 10, 0);
        Task t1 = buildTask("1", sameDate);
        Task t2 = buildTask("2", sameDate);
        List<Task> tasks = new ArrayList<>(List.of(t1, t2));

        // Act
        List<Task> result = strategy.filter(tasks, null);

        // Assert
        assertThat(result)
                .extracting(Task::getId)
                .containsExactly("1", "2");
    }
}
