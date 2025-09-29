package edu.dosw.model;

import java.util.UUID;

/**
 * Abstract base class representing a user in the system.
 * Provides common properties and methods for all user types.
 * Subclasses must implement role-specific authorization logic.
 */
public abstract class User {
    /** Unique identifier for the user */
    private String id;
    
    /** Display name of the user */
    private String userName;

    /**
     * Creates a new User with the specified username.
     * Automatically generates a unique ID for the user.
     *
     * @param userName The display name for the user
     */
    protected User(String userName) {
        this.id = UUID.randomUUID().toString();
        this.userName = userName;
    }

    /**
     * @return The unique identifier of the user
     */
    public String getId() { return id; }
    
    /**
     * @return The display name of the user
     */
    public String getUserName() { return userName; }
    
    /**
     * Sets the unique identifier of the user.
     * Use with caution as this can affect data integrity.
     *
     * @param id The new ID to set
     */
    public void setId(String id) { this.id = id; }
    
    /**
     * Updates the display name of the user.
     *
     * @param userName The new display name
     */
    public void setUserName(String userName) { this.userName = userName; }

    /**
     * Determines if the user has permission to create tasks.
     *
     * @return true if the user can create tasks, false otherwise
     */
    public abstract boolean canCreateTask();
    
    /**
     * Determines if the user has permission to update tasks.
     *
     * @return true if the user can update tasks, false otherwise
     */
    public abstract boolean canUpdateTask();
    
    /**
     * Determines if the user has permission to delete tasks.
     *
     * @return true if the user can delete tasks, false otherwise
     */
    public abstract boolean canDeleteTasks();

}
