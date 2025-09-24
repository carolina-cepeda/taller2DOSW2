package edu.dosw.model;

import edu.dosw.model.States;
import edu.dosw.model.Task;

import java.time.LocalDateTime;

public class TaskFactory {
    public static Task createTask(String title, String description, LocalDateTime date, States state) {
        return new Task(title, description, date, state);
    }
}
