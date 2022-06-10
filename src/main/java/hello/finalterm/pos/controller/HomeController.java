package hello.finalterm.pos.controller;

import hello.finalterm.pos.user.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping({"/"})
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        UserDto user = (UserDto) session.getAttribute("user");
        if (user != null) {
            System.out.println("user.getId() = " + user.getId());
        }
        model.addAttribute("user", user);

        return "home";
    }
}
