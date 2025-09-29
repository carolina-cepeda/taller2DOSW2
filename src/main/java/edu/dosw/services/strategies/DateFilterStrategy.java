package edu.dosw.services.strategies;

import edu.dosw.model.Task;
import java.util.Comparator;
import java.util.List;

/**
 * Sorts tasks by date in ascending order (earliest first).
 * Ignores the 'extra' parameter.
 */
public class DateFilterStrategy implements FilterStrategy {
    
    /**
     * Sorts the list of tasks by their date in ascending order.
     *
     * @param tasks List of tasks to be sorted
     * @param extra Not used in this implementation
     * @return The sorted list of tasks
     */
    @Override
    public List<Task> filter(List<Task> tasks, String extra) {
        tasks.sort(Comparator.comparing(Task::getDate));
        return tasks;
    }
}
