package edu.dosw.dto;

import jakarta.validation.constraints.NotNull;
import edu.dosw.model.Task;
import edu.dosw.model.States;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDTO(
        String id,
        @NotNull String title,
        String description,
        LocalDateTime date,
        String state
) {
    // Valores por defecto
    public TaskDTO {
        if (date == null) date = LocalDateTime.now();
        if (state == null) state = States.PENDING.name();
    }

    // Conversión de entidad a DTO
    public static TaskDTO fromTask(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDate(),
                task.getState() != null ? task.getState().name() : States.PENDING.name()
        );
    }

    // Conversión de DTO a entidad
    public Task toEntity() {
        Task task = new Task();
        task.setId(this.id != null ? this.id : UUID.randomUUID().toString());
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setDate(this.date);
        task.setState(this.state != null ? States.valueOf(this.state) : States.PENDING);
        return task;
    }
}
