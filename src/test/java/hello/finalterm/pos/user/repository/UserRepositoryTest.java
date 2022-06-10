package hello.finalterm.pos.user.repository;

import hello.finalterm.pos.user.entity.User;
import hello.finalterm.pos.user.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Slf4j
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void saveTest() throws Exception {
        //given
        User user = new User("a@a.com", "123", "a", UserRole.Staff.toString(), LocalDateTime.of(2022, 1, 1, 23, 59, 59));

        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getUserRole()).isEqualTo(user.getUserRole());
        assertThat(savedUser.getRegisterDateTime()).isEqualTo(user.getRegisterDateTime());
    }

    @Test
    public void saveDuplicateFailTest() throws Exception {
        //given
        User user = new User("a@a.com", "123", "a", UserRole.Staff.toString(), LocalDateTime.of(2022, 1, 1, 23, 59, 59));

        //when
        userRepository.save(user);

        //then
        assertThatThrownBy(() -> userRepository.save(user))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        User savedUser = userRepository.save(new User("a@a.com", "123", "a", UserRole.Staff.toString(), LocalDateTime.of(2022, 1, 1, 23, 59, 59)));

        //when
        List<User> userList = userRepository.findAll();

        //then
        for (User user : userList) {
            log.info("user={}", user);
        }
        assertThat(userList.size()).isEqualTo(1);
        assertThat(userList)
                .extracting("id")
                .contains(savedUser.getId());
        assertThat(userList)
                .extracting("email")
                .contains(savedUser.getEmail());
        assertThat(userList)
                .extracting("password")
                .contains(savedUser.getPassword());
        assertThat(userList)
                .extracting("name")
                .contains(savedUser.getName());
        assertThat(userList)
                .extracting("userRole")
                .contains(savedUser.getUserRole());
        assertThat(userList)
                .extracting("registerDateTime")
                .contains(savedUser.getRegisterDateTime());
    }

    @Test
    public void findByEmailTest() throws Exception {
        //given
        User savedUser = userRepository.save(new User("a@a.com", "123", "a", UserRole.Staff.toString(), LocalDateTime.of(2022, 1, 1, 23, 59, 59)));
        String email = savedUser.getEmail();

        //when
        User findUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException(User.class.getSimpleName() + "가 존재하지 않습니다"));

        //then
        assertThat(findUser.getId()).isEqualTo(savedUser.getId());
        assertThat(findUser.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(findUser.getPassword()).isEqualTo(savedUser.getPassword());
        assertThat(findUser.getName()).isEqualTo(savedUser.getName());
        assertThat(findUser.getUserRole()).isEqualTo(savedUser.getUserRole());
        assertThat(findUser.getRegisterDateTime()).isEqualTo(savedUser.getRegisterDateTime());
    }

    @Test
    public void findByEmailEmailNotExistFailTest() throws Exception {
        //given
        User savedUser = userRepository.save(new User("a@a.com", "123", "a", UserRole.Staff.toString(), LocalDateTime.of(2022, 1, 1, 23, 59, 59)));
        String email = savedUser.getEmail() + " fail";

        //when
        //then
        assertThatThrownBy(() -> userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException(User.class.getSimpleName() + "가 존재하지 않습니다")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(User.class.getSimpleName() + "가 존재하지 않습니다");
    }
}