package edu.dosw.services;

import edu.dosw.dto.TaskDTO;
import edu.dosw.model.Task;
import edu.dosw.repositories.TaskRepository;
import edu.dosw.services.strategies.DateFilterStrategy;
import edu.dosw.services.strategies.FilterStrategy;
import edu.dosw.services.strategies.KeyWordFilterStrategy;
import edu.dosw.services.strategies.StatusFilterStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final Map<String, FilterStrategy> filterStrategies;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.filterStrategies = new HashMap<>();
        this.filterStrategies.put("date", new DateFilterStrategy());
        this.filterStrategies.put("keyword", new KeyWordFilterStrategy());
        this.filterStrategies.put("status", new StatusFilterStrategy());
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByFilter(String filter, String extra) {
        return filterStrategies.get(filter).filter(getTasks(), extra);
    }

    public Task createTask(TaskDTO task) {
        return taskRepository.save(task.toEntity());
    }
}
