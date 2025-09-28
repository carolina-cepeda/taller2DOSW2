package edu.dosw.controller;

import edu.dosw.model.User;
import edu.dosw.model.UserType;
import edu.dosw.services.UserFactory;
import edu.dosw.services.TaskService;
import edu.dosw.model.Task;
import edu.dosw.dto.TaskDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserFactory userFactory;
    private final TaskService taskService;

    @Autowired
    public UserController(UserFactory userFactory, TaskService taskService) {
        this.userFactory = userFactory;
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public User createUser(@RequestParam String id,@RequestParam String username, @RequestParam UserType type) {
        return userFactory.generateUser(id,username, type);
    }

    @PostMapping("/{userType}/task")
    public Task createTask(@PathVariable UserType userType, @RequestBody TaskDTO task) {
        if (userType == UserType.ADMIN || userType == UserType.MEMBER) {
            return taskService.createTask(task);
        }
        throw new RuntimeException("No tienes permisos para crear tareas");
    }

    @GetMapping("/{userType}/tasks")
    public List<Task> getTasks(@PathVariable UserType userType) {
        return taskService.getTasks();
    }

    @GetMapping("/{userType}/tasks/{filter}/{extra}")
    public List<Task> getTasksByFilter(@PathVariable UserType userType,
                                       @PathVariable String filter,
                                       @PathVariable String extra) {
        if (userType == UserType.ADMIN) {
            return taskService.getTasksByFilter(filter, extra);
        }
        throw new RuntimeException("No tienes permisos para filtrar tareas");
    }
}
