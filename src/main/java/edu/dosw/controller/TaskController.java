package edu.dosw.controller;

import edu.dosw.dto.TaskDTO;
import edu.dosw.exception.ResponseHandler;
import edu.dosw.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{userId}/tasks")
    @Operation(summary = "Create a new task (only ADMIN, MEMBER)")
    public ResponseEntity createTask(@PathVariable String userId, @RequestBody TaskDTO task) {
        if (task == null){
            return ResponseHandler.generateErrorResponse("Task is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(taskService.createTask(task, userId));
    }

    @GetMapping("/{userId}/tasks")
    @Operation(summary = "Get all tasks (all users)")
    public ResponseEntity getTasks(@PathVariable String userId) {
        if (userId == null) {
            return ResponseHandler.generateErrorResponse("User id is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("Tasks", HttpStatus.OK, taskService.getTasks());
    }

    @GetMapping("/{userId}/tasks/{filter}/{extra}")
    @Operation(summary = "Get tasks by filter (only ADMIN)")
    public ResponseEntity getTasksByFilter(@PathVariable String userId,
                                       @PathVariable String filter,
                                       @PathVariable String extra) {
        if (filter == null || extra == null) {
            return ResponseHandler.generateErrorResponse("Filter or extra is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("Tasks", HttpStatus.OK, taskService.getTasksByFilter(filter, extra));
    }

    @DeleteMapping("/{userId}/tasks/{id}")
    @Operation(summary = "Delete a task (only ADMIN)")
    public ResponseEntity deleteTask(@PathVariable String userId, @PathVariable String id) {
        if (id == null) {
            return ResponseHandler.generateErrorResponse("Task id is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("Task deleted", HttpStatus.OK, taskService.deleteTask(userId, id));
    }
}
