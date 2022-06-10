package hello.finalterm.pos.user.entity;

import java.time.LocalDateTime;

public class User {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String userRole;

    private LocalDateTime registerDateTime;

    public User(String email, String password, String name, String userRole, LocalDateTime registerDateTime) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
        this.registerDateTime = registerDateTime;
    }

    public User(Long id, String email, String password, String name, String userRole, LocalDateTime registerDateTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
        this.registerDateTime = registerDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }

    public void setRegisterDateTime(LocalDateTime registerDateTime) {
        this.registerDateTime = registerDateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", userRole='" + userRole + '\'' +
                ", registerDateTime=" + registerDateTime +
                '}';
    }
}
