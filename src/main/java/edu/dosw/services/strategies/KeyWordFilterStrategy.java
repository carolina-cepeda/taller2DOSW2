package edu.dosw.services.strategies;

import edu.dosw.model.Task;
import java.util.List;

/**
 * Filters tasks by checking if their title contains a specific keyword.
 * The search is case-sensitive.
 */
public class KeyWordFilterStrategy implements FilterStrategy {

    /**
     * Filters tasks based on keyword presence in the title.
     *
     * @param tasks List of tasks to filter
     * @param keyword The keyword to search for in task titles
     * @return A new list containing only tasks whose title contains the keyword
     */
    @Override
    public List<Task> filter(List<Task> tasks, String keyword) {
        return tasks.stream()
                   .filter(task -> task.getTitle().contains(keyword))
                   .toList();
    }
}
