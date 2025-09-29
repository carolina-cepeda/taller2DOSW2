package edu.dosw.services.strategies;

import edu.dosw.model.States;
import edu.dosw.model.Task;
import java.util.List;

/**
 * Filters tasks by their status.
 * The status comparison is case-insensitive.
 */
public class StatusFilterStrategy implements FilterStrategy {

    /**
     * Filters tasks to only include those with the specified status.
     *
     * @param tasks List of tasks to filter
     * @param status The status to filter by (case-insensitive)
     * @return A new list containing only tasks with the specified status
     * @throws IllegalArgumentException if the status is not a valid States value
     */
    @Override
    public List<Task> filter(List<Task> tasks, String status) {
        States state = States.valueOf(status.toUpperCase());
        return tasks.stream()
                   .filter(task -> task.getState().equals(state))
                   .toList();
    }
}
