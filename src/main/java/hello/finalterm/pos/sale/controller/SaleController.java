package hello.finalterm.pos.sale.controller;

import hello.finalterm.pos.sale.dto.SaleCreateDto;
import hello.finalterm.pos.sale.dto.SaleDto;
import hello.finalterm.pos.sale.service.SaleService;
import hello.finalterm.pos.stock.dto.StockDto;
import hello.finalterm.pos.stock.service.StockService;
import hello.finalterm.pos.user.dto.UserLoginDto;
import hello.finalterm.pos.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SaleController {

    private final SaleService saleService;

    private final StockService stockService;

    private final UserService userService;

    public SaleController(SaleService saleService, StockService stockService, UserService userService) {
        this.saleService = saleService;
        this.stockService = stockService;
        this.userService = userService;
    }

    @GetMapping("/sale")
    public String saleForm(Model model, HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {
        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        model.addAttribute("user", userLoginDto.getUserDto());

        model.addAttribute("saleCreateDto", new SaleCreateDto());

        List<StockDto> allStock = stockService.findStockListJoinProduct();
        model.addAttribute("allStock", allStock);

        return "sale/saleForm";
    }

    @PostMapping("/sale")
    public String sale(SaleCreateDto saleCreateDto, Model model,
                       HttpServletRequest request, RedirectAttributes redirectAttributes) {

        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        if (saleCreateDto == null) {
            redirectAttributes.addAttribute("error", "saleCreateDto가 null입니다");
            return "redirect:/errorPage";
        }

        if (saleCreateDto.getSaleQuantity() == null) {
            redirectAttributes.addAttribute("error", "판매 수량이 비어있습니다.");
            return "redirect:/errorPage";
        }

        if (saleCreateDto.getSaleQuantity() <= 0) {
            redirectAttributes.addAttribute("error", "판매 수량이 0보다 작거나 같습니다.");
            return "redirect:/errorPage";
        }

        saleCreateDto.setSaleDateTime(LocalDateTime.now().withSecond(0).withNano(0));
        if (saleCreateDto.getSaleDateTime() == null) {
            redirectAttributes.addAttribute("error", "판매 날짜가 비어있습니다.");
            return "redirect:/errorPage";
        }

        if (isStringEmpty(saleCreateDto.getProductCode())) {
            redirectAttributes.addAttribute("error", "제품 코드가 비어있습니다.");
            return "redirect:/errorPage";
        }

        try {
            SaleDto saleDto = saleService.saleProduct(userLoginDto.getUserDto().getId(), saleCreateDto);

            model.addAttribute("saleDto", saleDto);

            int totalPrice = saleDto.getSaleQuantity() * saleDto.getProductPrice();
            model.addAttribute("totalPrice", totalPrice);
            return "sale/saleSuccess";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    private boolean isStringEmpty(String str) {
        return str == null || str.isBlank();
    }
}
