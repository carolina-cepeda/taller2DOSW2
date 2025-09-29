package edu.dosw.unit.services.strategies;

import edu.dosw.model.States;
import edu.dosw.model.Task;
import edu.dosw.services.strategies.StatusFilterStrategy;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StatusFilterStrategyTest {

    private final StatusFilterStrategy strategy = new StatusFilterStrategy();

    private Task buildTask(String id, States state) {
        Task task = new Task();
        task.setId(id);
        task.setTitle("Task " + id);
        task.setDate(LocalDateTime.now());
        task.setState(state);
        return task;
    }

    @Test
    void testFilter_shouldReturnTasksMatchingStatus() {
        // Arrange
        Task t1 = buildTask("1", States.PENDING);
        Task t2 = buildTask("2", States.COMPLETE);
        Task t3 = buildTask("3", States.PENDING);

        List<Task> tasks = List.of(t1, t2, t3);

        // Act
        List<Task> result = strategy.filter(tasks, "PENDING");

        // Assert
        assertThat(result)
                .extracting(Task::getId)
                .containsExactly("1", "3");
    }

    @Test
    void testFilter_shouldReturnEmptyListWhenNoTasksMatch() {
        // Arrange
        Task t1 = buildTask("1", States.COMPLETE);
        Task t2 = buildTask("2", States.COMPLETE);

        List<Task> tasks = List.of(t1, t2);

        // Act
        List<Task> result = strategy.filter(tasks, "PENDING");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void testFilter_shouldBeCaseInsensitive() {
        // Arrange
        Task t1 = buildTask("1", States.IN_PROGRESS);
        Task t2 = buildTask("2", States.COMPLETE);

        List<Task> tasks = List.of(t1, t2);

        // Act
        List<Task> result = strategy.filter(tasks, "in_progress");

        // Assert
        assertThat(result)
                .extracting(Task::getId)
                .containsExactly("1");
    }

    @Test
    void testFilter_shouldThrowExceptionForInvalidStatus() {
        // Arrange
        Task t1 = buildTask("1", States.COMPLETE);
        List<Task> tasks = List.of(t1);

        // Act + Assert
        assertThatThrownBy(() -> strategy.filter(tasks, "INVALID_STATUS"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
