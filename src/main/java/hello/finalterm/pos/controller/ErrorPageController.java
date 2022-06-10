package hello.finalterm.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorPageController {

    @RequestMapping("/errorPage")
    public String error(@RequestParam String error, Model model) {
        model.addAttribute("error", error);
        return "error/errorPage";
    }
}
