package edu.dosw.model;

public class MemberUser extends User {

    public MemberUser(String username) {
        super(username);
    }

    public boolean canCreateTask() {
        return true;
    }

    public boolean canDeleteTasks() {
        return false;
    }

    public boolean canUpdateTask() {
        return true;
    }
}
