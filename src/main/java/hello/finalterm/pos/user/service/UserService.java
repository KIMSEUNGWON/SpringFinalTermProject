package hello.finalterm.pos.user.service;

import hello.finalterm.pos.user.dto.UserDto;
import hello.finalterm.pos.user.dto.UserLoginDto;
import hello.finalterm.pos.user.dto.UserSigninDto;
import hello.finalterm.pos.user.dto.UserSignupDto;
import hello.finalterm.pos.user.entity.User;
import hello.finalterm.pos.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto signUp(UserSignupDto userSignupDto) {
        User findUser = userRepository.findByEmail(userSignupDto.getEmail()).orElse(null);
        if (findUser != null) {
            System.out.println("[warn] " + "이미 등록된 유저입니다.");
            throw new RuntimeException("이미 등록된 유저입니다.");
        }

        User newUser =
                new User(userSignupDto.getEmail(), userSignupDto.getPassword(), userSignupDto.getName(), userSignupDto.getUserRole(), LocalDateTime.now().withNano(0));
        User savedUser = userRepository.save(newUser);
        System.out.println("[success] " + savedUser.getName() + "님 회원가입이 완료됐습니다.");

        return UserDto.userToDto(savedUser);
    }

    public UserDto signIn(UserSigninDto userSigninDto) {
        User findUser = userRepository.findByEmail(userSigninDto.getEmail()).orElse(null);
        if (findUser == null) {
            System.out.println("[warn] " + "등록되지 않은 유저입니다.");
            throw new RuntimeException("등록되지 않은 유저입니다.");
        }
        if (!findUser.getPassword().equals(userSigninDto.getPassword())) {
            System.out.println("[warn] " + "비밀번호가 일치하지 않습니다.");
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        System.out.println("[success] " + " 로그인이 완료됐습니다.");
        System.out.println("[success] " + findUser.getName() + "님 반갑습니다");
        return UserDto.userToDto(findUser);
    }

    public List<User> findAllUser() {
        List<User> userList = userRepository.findAll();

        return userList;
    }

    public Optional<User> findUserByEmail(String email) {
        User findUser = userRepository.findByEmail(email).orElse(null);
        if (findUser == null) {
            System.out.println("[warn] " + "등록되지 않은 유저입니다.");
        }

        return Optional.ofNullable(findUser);
    }

    public Optional<User> findUserById(Long id) {
        User findUser = userRepository.findById(id).orElse(null);
        if (findUser == null) {
            System.out.println("[warn] " + "등록되지 않은 유저입니다.");
        }

        return Optional.ofNullable(findUser);
    }

    public UserLoginDto isUserLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        UserDto user = (UserDto) session.getAttribute("user");
        if (user == null) {
            return new UserLoginDto(false, null, "login 하지 않았습니다.");
        }

        return new UserLoginDto(true, user, "");
    }
}