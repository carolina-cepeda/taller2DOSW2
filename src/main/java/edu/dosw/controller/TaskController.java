package edu.dosw.controller;

import edu.dosw.dto.TaskDTO;
import edu.dosw.model.Task;
import edu.dosw.model.User;
import edu.dosw.model.UserType;
import edu.dosw.services.TaskService;
import edu.dosw.services.UserService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping("/{userId}/tasks")
    @Operation(summary = "Create a new task (only ADMIN, MEMBER)")
    public Task createTask(@PathVariable String userId, @RequestBody TaskDTO task) {
        User user = userService.getUserById(userId).orElse(null);

        if (user != null && (user.getType() == UserType.ADMIN || user.getType() == UserType.MEMBER)) {
            return taskService.createTask(task);
        }
        return null;
    }

    @GetMapping("/{userId}/tasks")
    @Operation(summary = "Get all tasks (all users)")
    public List<Task> getTasks(@PathVariable String userId) {
        return taskService.getTasks();
    }

    @GetMapping("/{userId}/tasks/{filter}/{extra}")
    @Operation(summary = "Get tasks by filter (only ADMIN)")
    public List<Task> getTasksByFilter(@PathVariable String userId,
                                       @PathVariable String filter,
                                       @PathVariable String extra) {
        User user = userService.getUserById(userId).orElse(null);

        if (user != null && user.getType() == UserType.ADMIN) {
            return taskService.getTasksByFilter(filter, extra);
        }
        return null;
    }
}
