package edu.dosw.model;

public class AdminUser extends User {
    public AdminUser(String username) {
        super(username);
    }

    public boolean canCreateTask() {
        return true;
    }

    public boolean canDeleteTasks() {
        return true;
    }

    public boolean canUpdateTask() {
        return true;
    }
}
