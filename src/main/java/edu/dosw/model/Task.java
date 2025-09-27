package edu.dosw.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Tasks")
public class Task {
    @Id
    private String id;

    private String title;
    private String description;
    private LocalDateTime date;
    private States state;

    public Task() {}

    public Task(String title, String description, LocalDateTime date, States state) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.state = state;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDate() { return date; }
    public States getState() { return state; }

    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setState(States state) { this.state = state; }
}
