package edu.dosw.model;

/**
 * Represents a guest user in the system with limited privileges.
 * Guest users have read-only access and cannot modify any tasks.
 */
public class GuestUser extends User {

    /**
     * Creates a new GuestUser with the specified username.
     *
     * @param username The display name for the guest user
     */
    public GuestUser(String username) {
        super(username);
    }

    /**
     * Determines if the guest can create tasks.
     * Guest users cannot create tasks.
     *
     * @return Always returns false for guest users
     */
    @Override
    public boolean canCreateTask() {
        return false;
    }

    /**
     * Determines if the guest can delete tasks.
     * Guest users cannot delete tasks.
     *
     * @return Always returns false for guest users
     */
    @Override
    public boolean canDeleteTasks() {
        return false;
    }

    /**
     * Determines if the guest can update tasks.
     * Guest users cannot update tasks.
     *
     * @return Always returns false for guest users
     */
    @Override
    public boolean canUpdateTask() {
        return false;
    }
}
