package edu.dosw.model;

import java.util.UUID;

public abstract class User {
    private String id;
    private String userName;

    protected User(String userName) {
        this.id = UUID.randomUUID().toString();
        this.userName = userName;
    }

    public String getId() { return id; }
    public String getUserName() { return userName; }
    public void setId(String id) { this.id = id; }
    public void setUserName(String userName) { this.userName = userName; }

    public abstract boolean canCreateTask();
    public abstract boolean canUpdateTask();
    public abstract boolean canDeleteTasks();

}
