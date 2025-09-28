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


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final Map<String, FilterStrategy> filterStrategies;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.filterStrategies = Map.of(
        "date", new DateFilterStrategy(),
        "keyword", new KeyWordFilterStrategy(),
        "status", new StatusFilterStrategy());
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByFilter(String filter, String extra) {
        return filterStrategies.get(filter).filter(getTasks(), extra);
    }

    public Task createTask(TaskDTO task, String userId) {
        User user = userService.getUserById(userId).orElse(null);
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

    public boolean deleteTask(String userId, String taskId) {
        User user = userService.getUserById(userId).orElse(null);
        if (user == null || !user.canDeleteTasks()) {
            throw new RuntimeException("User is not authorized to delete tasks");
        }
        taskRepository.deleteById(taskId);
        return taskRepository.findById(taskId).isEmpty();
    }
}
