package edu.dosw.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Tasks")
public class Task {
    @Id
    private String title;

    private String description;
    private LocalDateTime date;
    private States state;

    public Task(String title, String description, LocalDateTime date, States state) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.state = state;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDate() { return date; }
    public States getState() { return state; }
    public void setState(States state) { this.state = state; }

    @Override
    public String toString() {
        return title + " [" + state + "]";
    }

}
