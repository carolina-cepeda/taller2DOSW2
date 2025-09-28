package edu.dosw.dto;

import java.time.LocalDateTime;

public record UpdateTaskDTO(
        String title,
        String description,
        LocalDateTime date,
        String state
) {}