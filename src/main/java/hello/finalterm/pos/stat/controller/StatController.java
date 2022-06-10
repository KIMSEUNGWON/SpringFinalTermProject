package hello.finalterm.pos.stat.controller;

import hello.finalterm.pos.stat.dto.StatCreateDto;
import hello.finalterm.pos.stat.dto.TopSellingProductNameAndQuantity;
import hello.finalterm.pos.stat.dto.ValidateDto;
import hello.finalterm.pos.stat.service.StatService;
import hello.finalterm.pos.user.dto.UserLoginDto;
import hello.finalterm.pos.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/stat")
public class StatController {

    private final StatService statService;

    private final UserService userService;

    public StatController(StatService statService, UserService userService) {
        this.statService = statService;
        this.userService = userService;
    }

    @GetMapping
    public String statPage(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        return "stat/statPage";
    }

    @GetMapping("/count")
    public String countForm(@RequestParam String duration, Model model,
                            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        if (!isDurationValidate(duration)) {
            redirectAttributes.addAttribute("error", "duration not match");
            return "redirect:/errorPage";
        }

        model.addAttribute("user", userLoginDto.getUserDto());

        model.addAttribute("duration", duration);
        model.addAttribute("statCreateDto", new StatCreateDto());

        return "stat/count/countForm";
    }

    @PostMapping("/count")
    public String count(@RequestParam String duration, StatCreateDto statCreateDto, Model model,
                        HttpServletRequest request, RedirectAttributes redirectAttributes) {

        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        if (!isDurationValidate(duration)) {
            redirectAttributes.addAttribute("error", "duration not match");
            return "redirect:/errorPage";
        }

        ValidateDto validateDto = validateStatCreateDto(duration, statCreateDto);
        if (!validateDto.isValidate()) {
            redirectAttributes.addAttribute("error", validateDto.getErrorCode());
            return "redirect:/errorPage";
        }

        try {
            LocalDate startDate = LocalDate.parse(statCreateDto.getStartDate());
            LocalDate endDate = LocalDate.parse(statCreateDto.getEndDate());
            Long count = statService.totalSellingCount(startDate, endDate);
            model.addAttribute("count", count);
            model.addAttribute("duration", duration);

            return "stat/count/countSuccess";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/income")
    public String incomeForm(@RequestParam String duration, Model model,
                             HttpServletRequest request, RedirectAttributes redirectAttributes) {
        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        if (!isDurationValidate(duration)) {
            redirectAttributes.addAttribute("error", "duration not match");
            return "redirect:/errorPage";
        }

        model.addAttribute("user", userLoginDto.getUserDto());

        model.addAttribute("duration", duration);
        model.addAttribute("statCreateDto", new StatCreateDto());

        return "stat/income/incomeForm";
    }

    @PostMapping("/income")
    public String income(@RequestParam String duration, StatCreateDto statCreateDto, Model model,
                         HttpServletRequest request, RedirectAttributes redirectAttributes) {

        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        if (!isDurationValidate(duration)) {
            redirectAttributes.addAttribute("error", "duration not match");
            return "redirect:/errorPage";
        }

        ValidateDto validateDto = validateStatCreateDto(duration, statCreateDto);
        if (!validateDto.isValidate()) {
            redirectAttributes.addAttribute("error", validateDto.getErrorCode());
            return "redirect:/errorPage";
        }

        try {
            LocalDate startDate = LocalDate.parse(statCreateDto.getStartDate());
            LocalDate endDate = LocalDate.parse(statCreateDto.getEndDate());
            Long income = statService.totalIncome(startDate, endDate);
            model.addAttribute("income", income);
            model.addAttribute("duration", duration);

            return "stat/income/incomeSuccess";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/product")
    public String topSellingProductForm(@RequestParam String duration, Model model,
                                        HttpServletRequest request, RedirectAttributes redirectAttributes) {
        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        if (!isDurationValidate(duration)) {
            redirectAttributes.addAttribute("error", "duration not match");
            return "redirect:/errorPage";
        }

        model.addAttribute("user", userLoginDto.getUserDto());

        model.addAttribute("duration", duration);
        model.addAttribute("statCreateDto", new StatCreateDto());

        return "stat/product/topSellingProductForm";
    }

    @PostMapping("/product")
    public String topSellingProduct(@RequestParam String duration, StatCreateDto statCreateDto, Model model,
                                    HttpServletRequest request, RedirectAttributes redirectAttributes) {

        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        if (!isDurationValidate(duration)) {
            redirectAttributes.addAttribute("error", "duration not match");
            return "redirect:/errorPage";
        }

        ValidateDto validateDto = validateStatCreateDto(duration, statCreateDto);
        if (!validateDto.isValidate()) {
            redirectAttributes.addAttribute("error", validateDto.getErrorCode());
            return "redirect:/errorPage";
        }

        try {
            LocalDate startDate = LocalDate.parse(statCreateDto.getStartDate());
            LocalDate endDate = LocalDate.parse(statCreateDto.getEndDate());
            List<TopSellingProductNameAndQuantity> topSellingProductList = statService.findTopSellingProduct(startDate, endDate);
            model.addAttribute("topSellingProductList", topSellingProductList);
            model.addAttribute("duration", duration);

            return "stat/product/topSellingProductSuccess";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    private ValidateDto validateStatCreateDto(String duration, StatCreateDto statCreateDto) {
        if (statCreateDto == null) {
            return new ValidateDto(false, "saleCreateDto가 null입니다");
        }

        if (statCreateDto.getStartDate() == null) {
            return new ValidateDto(false, "시작일이 비어있습니다.");
        }

        if (statCreateDto.getStartDate().isBlank()) {
            return new ValidateDto(false, "종료일이 비어있습니다.");
        }

        switch (duration) {
            case "date":
                statCreateDto.setEndDate(statCreateDto.getStartDate());
                break;
            case "week":
                LocalDate startDate = LocalDate.parse(statCreateDto.getStartDate());
                LocalDate endDate = startDate.plusDays(6);
                statCreateDto.setEndDate(String.valueOf(endDate));
                break;
            case "month":
                YearMonth stand = YearMonth.parse(statCreateDto.getStartDate());
                statCreateDto.setStartDate(String.valueOf(stand.atDay(1)));
                statCreateDto.setEndDate(String.valueOf(stand.atEndOfMonth()));
                break;
        }

        if (statCreateDto.getEndDate() == null) {
            return new ValidateDto(false, "시작일이 비어있습니다.");
        }

        if (statCreateDto.getEndDate().isBlank()) {
            return new ValidateDto(false, "종료일이 비어있습니다.");
        }

        return new ValidateDto(true, "a");
    }

    private boolean isDurationValidate(String duration) {
        switch (duration) {
            case "date":
            case "month":
            case "week":
                return true;
        }

        return false;
    }
}
