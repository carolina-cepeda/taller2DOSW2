package edu.dosw.model;

/**
 * Represents an administrator user in the system with full privileges.
 * Admin users have complete control over tasks including creation, 
 * modification, and deletion.
 */
public class AdminUser extends User {
    /**
     * Creates a new AdminUser with the specified username.
     *
     * @param username The display name for the administrator
     */
    public AdminUser(String username) {
        super(username);
    }

    /**
     * Determines if the admin can create tasks.
     * Admin users always have permission to create tasks.
     *
     * @return Always returns true for admin users
     */
    @Override
    public boolean canCreateTask() {
        return true;
    }

    /**
     * Determines if the admin can delete tasks.
     * Admin users always have permission to delete tasks.
     *
     * @return Always returns true for admin users
     */
    @Override
    public boolean canDeleteTasks() {
        return true;
    }

    /**
     * Determines if the admin can update tasks.
     * Admin users always have permission to update tasks.
     *
     * @return Always returns true for admin users
     */
    @Override
    public boolean canUpdateTask() {
        return true;
    }
}
