package edu.dosw.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testAdminUserPermissions() {
        AdminUser admin = new AdminUser("adminUser");

        assertNotNull(admin.getId());
        assertEquals("adminUser", admin.getUsername());

        assertTrue(admin.canCreateTask());
        assertTrue(admin.canUpdateTask());
        assertTrue(admin.canDeleteTasks());
    }

    @Test
    void testGuestUserPermissions() {
        GuestUser guest = new GuestUser("guestUser");

        assertNotNull(guest.getId());
        assertEquals("guestUser", guest.getUsername());

        assertFalse(guest.canCreateTask());
        assertFalse(guest.canUpdateTask());
        assertFalse(guest.canDeleteTasks());
    }

    @Test
    void testMemberUserPermissions() {
        MemberUser member = new MemberUser("memberUser");

        assertNotNull(member.getId());
        assertEquals("memberUser", member.getUsername());

        assertTrue(member.canCreateTask());
        assertTrue(member.canUpdateTask());
        assertFalse(member.canDeleteTasks());
    }

    @Test
    void testSettersAndGetters() {
        AdminUser user = new AdminUser("originalUser");

        String oldId = user.getId();
        user.setId("custom-id");
        user.setUsername("newUser");

        assertEquals("custom-id", user.getId());
        assertEquals("newUser", user.getUsername());
        assertNotEquals(oldId, user.getId()); // id fue reemplazado manualmente
    }

    @Test
    void testDifferentUsersHaveDifferentIds() {
        AdminUser admin = new AdminUser("admin");
        GuestUser guest = new GuestUser("guest");
        MemberUser member = new MemberUser("member");

        assertNotEquals(admin.getId(), guest.getId());
        assertNotEquals(guest.getId(), member.getId());
        assertNotEquals(admin.getId(), member.getId());
    }
}
