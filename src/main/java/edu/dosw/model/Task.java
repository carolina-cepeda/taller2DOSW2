package edu.dosw.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a task in the task management system.
 * This class is mapped to the "Tasks" collection in MongoDB.
 */
@Document(collection = "Tasks")
public class Task {
    /** Unique identifier for the task */
    @Id
    private String id;

    /** The title of the task */
    private String title;
    
    /** Detailed description of the task */
    private String description;
    
    /** The due date and time of the task */
    private LocalDateTime date;
    
    /** The current state of the task */
    private States state;

    /**
     * Default constructor required by JPA.
     */
    public Task() {}

    /**
     * Creates a new Task with the specified details.
     *
     * @param title The title of the task
     * @param description Detailed description of the task
     * @param date The due date and time of the task
     * @param state The initial state of the task
     */
    public Task(String title, String description, LocalDateTime date, States state) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.state = state;
    }

    /**
     * @return The unique identifier of the task
     */
    public String getId() { return id; }
    
    /**
     * @return The title of the task
     */
    public String getTitle() { return title; }
    
    /**
     * @return The detailed description of the task
     */
    public String getDescription() { return description; }
    
    /**
     * @return The due date and time of the task
     */
    public LocalDateTime getDate() { return date; }
    
    /**
     * @return The current state of the task
     */
    public States getState() { return state; }

    /**
     * Sets the unique identifier of the task.
     * 
     * @param id The unique identifier to set
     */
    public void setId(String id) { this.id = id; }
    
    /**
     * Sets the title of the task.
     * 
     * @param title The title to set
     */
    public void setTitle(String title) { this.title = title; }
    
    /**
     * Sets the description of the task.
     * 
     * @param description The description to set
     */
    public void setDescription(String description) { this.description = description; }
    
    /**
     * Sets the due date and time of the task.
     * 
     * @param date The due date and time to set
     */
    public void setDate(LocalDateTime date) { this.date = date; }
    
    /**
     * Sets the state of the task.
     * 
     * @param state The state to set
     */
    public void setState(States state) { this.state = state; }
}
