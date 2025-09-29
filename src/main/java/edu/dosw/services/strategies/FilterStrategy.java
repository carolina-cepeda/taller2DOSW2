package edu.dosw.services.strategies;

import edu.dosw.model.Task;
import java.util.List;

/**
 * Defines the contract for task filtering strategies.
 * Implementations should provide specific filtering logic.
 */
public interface FilterStrategy {
    
    /**
     * Applies filtering logic to a list of tasks.
     * 
     * @param tasks The list of tasks to filter
     * @param param Additional parameter for filtering (implementation-specific)
     * @return The filtered list of tasks
     */
    List<Task> filter(List<Task> tasks, String param);
}
