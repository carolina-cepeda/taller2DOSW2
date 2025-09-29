package edu.dosw.services;

import edu.dosw.dto.TaskDTO;
import edu.dosw.dto.UpdateTaskDTO;
import edu.dosw.model.States;
import edu.dosw.model.Task;
import edu.dosw.model.User;
import edu.dosw.repositories.TaskRepository;
import edu.dosw.services.strategies.DateFilterStrategy;
import edu.dosw.services.strategies.FilterStrategy;
import edu.dosw.services.strategies.KeyWordFilterStrategy;
import edu.dosw.services.strategies.StatusFilterStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service class for task-related operations.
 * Handles task creation, updates, deletion, and filtering.
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final Map<String, FilterStrategy> filterStrategies;
    private final UserService userService;

    /**
     * Initializes the TaskService with required dependencies.
     * Sets up the available filter strategies.
     *
     * @param taskRepository Repository for task data access
     * @param userService Service for user-related operations
     */
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.filterStrategies = Map.of(
            "date", new DateFilterStrategy(),
            "keyword", new KeyWordFilterStrategy(),
            "status", new StatusFilterStrategy()
        );
    }

    /**
     * Retrieves all tasks.
     *
     * @return List of all tasks
     */
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    /**
     * Retrieves tasks filtered by the specified criteria.
     *
     * @param filter Type of filter to apply ("date", "keyword", or "status")
     * @param extra Additional parameter required by the filter
     * @return Filtered list of tasks
     * @throws IllegalArgumentException if the filter type is not supported
     */
    public List<Task> getTasksByFilter(String filter, String extra) {
        FilterStrategy strategy = filterStrategies.get(filter);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported filter type: " + filter);
        }
        return strategy.filter(getTasks(), extra);
    }

    /**
     * Creates a new task after verifying user permissions.
     *
     * @param task The task data to create
     * @param userId ID of the user creating the task
     * @return The created task
     * @throws RuntimeException if user is not authorized to create tasks
     */
    public Task createTask(TaskDTO task, String userId) {
        User user = userService.getUserById(userId);
        if (user == null || !user.canCreateTask()) {
            throw new RuntimeException("User is not authorized to create tasks");
        }
        return taskRepository.save(task.toEntity());
    }

    public Task updateTask(UpdateTaskDTO updateTask, String taskId, String userId) {
        User user = userService.getUserById(userId).orElse(null);
        if (user == null || !user.canUpdateTask()) {
            throw new RuntimeException("User is not authorized to update tasks");
        }

        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            throw new RuntimeException("Task not found by the given ID");
        }

        if (updateTask.title() != null)       task.setTitle(updateTask.title());
        if (updateTask.description() != null) task.setDescription(updateTask.description());
        if (updateTask.date() != null)        task.setDate(updateTask.date());
        if (updateTask.state() != null)       task.setState(States.valueOf(updateTask.state()));

        return taskRepository.save(task);
    }

    /**
     * Deletes a task after verifying user permissions.
     *
     * @param userId ID of the user attempting to delete the task
     * @param taskId ID of the task to delete
     * @return true if the task was successfully deleted, false otherwise
     * @throws RuntimeException if user is not authorized to delete tasks
     */
    public boolean deleteTask(String userId, String taskId) {
        User user = userService.getUserById(userId);
        if (user == null || !user.canDeleteTasks()) {
            throw new RuntimeException("User is not authorized to delete tasks");
        }
        taskRepository.deleteById(taskId);
        return taskRepository.findById(taskId).isEmpty();
    }
}
