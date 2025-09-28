package edu.dosw.model;

public class User {
    private String id;
    private String username;
    private UserType type;

    public User() {}

    public User(String id, String username, UserType type) {
        this.id = id;
        this.username = username;
        this.type = type;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public UserType getType() { return type; }

    public void setId(String id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setType(UserType type) { this.type = type; }
}
