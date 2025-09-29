package edu.dosw.dto;

import jakarta.validation.constraints.NotNull;
import edu.dosw.model.Task;
import edu.dosw.model.States;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for {@link Task} entities.
 * This class is used to transfer task data between layers of the application.
 * It provides validation and conversion methods to/from the entity model.
 *
 * @param id The unique identifier of the task (auto-generated if not provided)
 * @param title The title of the task (required)
 * @param description The detailed description of the task (optional)
 * @param date The due date and time of the task (defaults to current time if not provided)
 * @param state The current state of the task (defaults to PENDING if not provided)
 */
public record TaskDTO(
        String id,
        @NotNull(message = "Task title cannot be null") String title,
        String description,
        LocalDateTime date,
        String state
) {

    /**
     * Canonical constructor with default values.
     * Sets default values for date (current time) and state (PENDING) if not provided.
     */
    public TaskDTO {
        if (date == null) date = LocalDateTime.now();
        if (state == null) state = States.PENDING.name();
    }


    /**
     * Creates a new TaskDTO from a Task entity.
     *
     * @param task The Task entity to convert from
     * @return A new TaskDTO with data from the provided Task
     * @throws NullPointerException if the task parameter is null
     */
    public static TaskDTO fromTask(Task task) {
        if (task == null) {
            throw new NullPointerException("Task cannot be null");
        }
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDate(),
                task.getState() != null ? task.getState().name() : States.PENDING.name()
        );
    }


    /**
     * Converts this DTO to a Task entity.
     * Generates a new UUID if no ID is provided.
     *
     * @return A new Task entity with data from this DTO
     * @throws IllegalArgumentException if the state string is not a valid States enum value
     */
    public Task toEntity() {
        Task task = new Task();
        task.setId(this.id != null ? this.id : UUID.randomUUID().toString());
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setDate(this.date);
        
        try {
            task.setState(this.state != null ? States.valueOf(this.state) : States.PENDING);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid task state: " + this.state, e);
        }
        
        return task;
    }
}
