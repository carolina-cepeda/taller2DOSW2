package edu.dosw.model;

/**
 * Represents a member user in the system with standard privileges.
 * Member users can create and update tasks but cannot delete them.
 */
public class MemberUser extends User {

    /**
     * Creates a new MemberUser with the specified username.
     *
     * @param username The display name for the member user
     */
    public MemberUser(String username) {
        super(username);
    }

    /**
     * Determines if the member can create tasks.
     * Member users have permission to create tasks.
     *
     * @return Always returns true for member users
     */
    @Override
    public boolean canCreateTask() {
        return true;
    }

    /**
     * Determines if the member can delete tasks.
     * Member users do not have permission to delete tasks.
     *
     * @return Always returns false for member users
     */
    @Override
    public boolean canDeleteTasks() {
        return false;
    }

    /**
     * Determines if the member can update tasks.
     * Member users have permission to update tasks.
     *
     * @return Always returns true for member users
     */
    @Override
    public boolean canUpdateTask() {
        return true;
    }
}
