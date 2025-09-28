package edu.dosw.model;

import java.util.UUID;

public abstract class User {
    private String id;
    private String username;

    protected User(String username) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }

    public void setId(String id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }

    public abstract boolean canCreateTask();
    public abstract boolean canUpdateTask();
    public abstract boolean canDeleteTasks();

}
