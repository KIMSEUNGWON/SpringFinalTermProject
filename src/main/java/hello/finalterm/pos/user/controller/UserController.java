package hello.finalterm.pos.user.controller;

import hello.finalterm.pos.user.dto.UserDto;
import hello.finalterm.pos.user.dto.UserLoginDto;
import hello.finalterm.pos.user.dto.UserSigninDto;
import hello.finalterm.pos.user.dto.UserSignupDto;
import hello.finalterm.pos.user.entity.UserRole;
import hello.finalterm.pos.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("userSignupDto", new UserSignupDto());
        model.addAttribute("userRoleType", UserRole.values());
        return "user/signupForm";
    }

    @PostMapping("/sign-up")
    public String signUp(UserSignupDto userSignupDto,
                         Model model, RedirectAttributes redirectAttributes) {

        if (userSignupDto == null) {
            redirectAttributes.addAttribute("error", "userSignupDto가 null입니다");
            return "redirect:/errorPage";
        }

        if (isStringEmpty(userSignupDto.getEmail())) {
//            redirectAttributes.addAttribute("error", "userSignupDto.getEmail()가 blank입니다");
            redirectAttributes.addAttribute("error", "이메일이 비어있습니다.");
            return "redirect:/errorPage";
        }

        if (isStringEmpty(userSignupDto.getPassword())) {
            redirectAttributes.addAttribute("error", "비밀번호가 비어있습니다.");
            return "redirect:/errorPage";
        }

        if (isStringEmpty(userSignupDto.getName())) {
            redirectAttributes.addAttribute("error", "이름이 비어있습니다.");
            return "redirect:/errorPage";
        }

        if (isStringEmpty(userSignupDto.getUserRole())) {
            redirectAttributes.addAttribute("error", "역할이 비어있습니다.");
            return "redirect:/errorPage";
        }

        try {
            UserDto userDto = userService.signUp(userSignupDto);
            model.addAttribute("userDto", userDto);
            return "user/signupSuccess";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/sign-in")
    public String signInForm(Model model) {
        model.addAttribute("userSigninDto", new UserSigninDto());
        return "user/signinForm";
    }

    @PostMapping("/sign-in")
    public String signUp(UserSigninDto userSigninDto, Model model,
                         HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if (userSigninDto == null) {
            redirectAttributes.addAttribute("error", "userSigninDto가 null입니다");
            return "redirect:/errorPage";
        }

        if (isStringEmpty(userSigninDto.getEmail())) {
            redirectAttributes.addAttribute("error", "이메일이 비어있습니다.");
            return "redirect:/errorPage";
        }

        if (isStringEmpty(userSigninDto.getPassword())) {
            redirectAttributes.addAttribute("error", "비밀번호가 비어있습니다.");
            return "redirect:/errorPage";
        }

        try {
            UserDto userDto = userService.signIn(userSigninDto);
            model.addAttribute("userDto", userDto);

            HttpSession session = request.getSession(true);
            session.setAttribute("user", userDto);

            return "user/signinSuccess";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    @PostMapping("/sign-out")
    public String signOut(Model model,
                          HttpServletRequest request, RedirectAttributes redirectAttributes) {

        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        try {
            HttpSession session = request.getSession();
            session.invalidate();

            return "user/signoutSuccess";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    private boolean isStringEmpty(String str) {
        return str == null || str.isBlank();
    }
}
