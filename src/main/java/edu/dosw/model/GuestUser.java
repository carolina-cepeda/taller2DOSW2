package edu.dosw.model;

public class GuestUser extends User {

    public GuestUser(String username) {
        super(username);
    }

    public boolean canCreateTask() {
        return false;
    }

    public boolean canDeleteTasks() {
        return false;
    }

    @Override
    public boolean canUpdateTask() {
        return false;
    }
}
