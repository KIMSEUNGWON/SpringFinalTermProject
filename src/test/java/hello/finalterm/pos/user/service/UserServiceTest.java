package hello.finalterm.pos.user.service;

import hello.finalterm.pos.user.dto.UserDto;
import hello.finalterm.pos.user.dto.UserSigninDto;
import hello.finalterm.pos.user.dto.UserSignupDto;
import hello.finalterm.pos.user.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Slf4j
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void signUpTest() throws Exception {
        //given
        UserSignupDto userSignupDto =
                new UserSignupDto("a@a.com", "123", "a", UserRole.Staff.toString());

        //when
        UserDto userDto = userService.signUp(userSignupDto);

        //then
        assertThat(userDto.getEmail()).isEqualTo(userSignupDto.getEmail());
        assertThat(userDto.getPassword()).isEqualTo(userSignupDto.getPassword());
        assertThat(userDto.getName()).isEqualTo(userSignupDto.getName());
        assertThat(userDto.getUserRole()).isEqualTo(UserRole.valueOf(userSignupDto.getUserRole()));
    }

    @Test
    public void signUpDuplicateFailTest() throws Exception {
        //given
        UserSignupDto userSignupDto =
                new UserSignupDto("a@a.com", "123", "a", UserRole.Staff.toString());

        //when
        userService.signUp(userSignupDto);

        //then
        assertThatThrownBy(() -> userService.signUp(userSignupDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 등록된 유저입니다.");
    }

    @Test
    public void signInTest() throws Exception {
        //given
        UserDto savedUser = userService.signUp(new UserSignupDto("a@a.com", "123", "a", UserRole.Staff.toString()));

        UserSigninDto userSigninDto =
                new UserSigninDto(savedUser.getEmail(), savedUser.getPassword());

        //when
        UserDto userDto = userService.signIn(userSigninDto);

        //then
        assertThat(userDto.getId()).isEqualTo(savedUser.getId());
        assertThat(userDto.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(userDto.getPassword()).isEqualTo(savedUser.getPassword());
        assertThat(userDto.getName()).isEqualTo(savedUser.getName());
        assertThat(userDto.getUserRole()).isEqualTo(savedUser.getUserRole());
        assertThat(userDto.getRegisterDateTime()).isEqualTo(savedUser.getRegisterDateTime());
    }

    @Test
    public void signInEmailNotExistFailTest() throws Exception {
        //given
        UserDto savedUser = userService.signUp(new UserSignupDto("a@a.com", "123", "a", UserRole.Staff.toString()));

        UserSigninDto userSigninDto =
                new UserSigninDto(savedUser.getEmail() + " fail", savedUser.getPassword());

        //when
        //then
        assertThatThrownBy(() -> userService.signIn(userSigninDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("등록되지 않은 유저입니다.");
    }

    @Test
    public void signInPasswordNotMatchFailTest() throws Exception {
        //given
        UserDto savedUser = userService.signUp(new UserSignupDto("a@a.com", "123", "a", UserRole.Staff.toString()));

        UserSigninDto userSigninDto =
                new UserSigninDto(savedUser.getEmail(), savedUser.getPassword() + " fail");

        //when
        //then
        assertThatThrownBy(() -> userService.signIn(userSigninDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}