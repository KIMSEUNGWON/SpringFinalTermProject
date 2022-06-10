package hello.finalterm.pos.user.dto;

import hello.finalterm.pos.user.entity.User;
import hello.finalterm.pos.user.entity.UserRole;

import java.time.LocalDateTime;

public class UserDto {

    private Long id;

    private String email;
    private String password;
    private String name;
    private UserRole userRole;
    private LocalDateTime registerDateTime;

    public UserDto(String email, String password, String name, UserRole userRole, LocalDateTime registerDateTime) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
        this.registerDateTime = registerDateTime;
    }

    public static UserDto userToDto(User user) {
        return new UserDto(user);
    }

    private UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.userRole = UserRole.valueOf(user.getUserRole());
        this.registerDateTime = user.getRegisterDateTime();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }
}