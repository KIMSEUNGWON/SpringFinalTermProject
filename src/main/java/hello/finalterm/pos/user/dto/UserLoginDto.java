package hello.finalterm.pos.user.dto;

public class UserLoginDto {

    private Boolean isUserLogin;

    private UserDto userDto;

    private String errorMessage;

    public UserLoginDto(Boolean isUserLogin, UserDto userDto, String errorMessage) {
        this.isUserLogin = isUserLogin;
        this.userDto = userDto;
        this.errorMessage = errorMessage;
    }

    public Boolean getUserLogin() {
        return isUserLogin;
    }

    public void setUserLogin(Boolean userLogin) {
        isUserLogin = userLogin;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}