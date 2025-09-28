package edu.dosw.controller;

import edu.dosw.dto.TaskDTO;
import edu.dosw.model.Task;
import edu.dosw.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    @Operation(summary = "Create a new task")
    public Task createTask(@RequestBody TaskDTO task) {
        return taskService.createTask(task);
    }

    @GetMapping("/tasks")
    @Operation(summary = "Get all tasks")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/tasks/{filter}/{extra}")
    @Operation(summary = "Get tasks by filter")
    public List<Task> getTasksByFilter(@PathVariable String filter, @PathVariable String extra) {
        return taskService.getTasksByFilter(filter, extra);
    }
}
