package hello.finalterm.pos.stock.controller;

import hello.finalterm.pos.product.entity.Product;
import hello.finalterm.pos.product.service.ProductService;
import hello.finalterm.pos.stock.dto.StockDto;
import hello.finalterm.pos.stock.dto.StockInDto;
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
public class StockController {

    private final StockService stockService;

    private final ProductService productService;

    private final UserService userService;

    public StockController(StockService stockService, ProductService productService, UserService userService) {
        this.stockService = stockService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/stock-in")
    public String stockInForm(Model model, HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        model.addAttribute("user", userLoginDto.getUserDto());

        model.addAttribute("stockInDto", new StockInDto());

        List<Product> allProduct = productService.findAllProduct();
        model.addAttribute("allProduct", allProduct);
        return "stock/stockinForm";
    }

    @PostMapping("/stock-in")
    public String stockIn(StockInDto stockInDto, Model model,
                          HttpServletRequest request, RedirectAttributes redirectAttributes) {

        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        if (stockInDto == null) {
            redirectAttributes.addAttribute("error", "stockInDto가 null입니다");
            return "redirect:/errorPage";
        }

        if (stockInDto.getStockQuantity() == null) {
            redirectAttributes.addAttribute("error", "입고 수량이 비어있습니다.");
            return "redirect:/errorPage";
        }

        stockInDto.setStockDateTime(LocalDateTime.now().withSecond(0).withNano(0));
        if (stockInDto.getStockDateTime() == null) {
            redirectAttributes.addAttribute("error", "입고 날짜가 비어있습니다.");
            return "redirect:/errorPage";
        }

        if (stockInDto.getProductId() == null) {
            redirectAttributes.addAttribute("error", "입고 제품이 비어있습니다.");
            return "redirect:/errorPage";
        }

        try {
            StockDto stockDto =
                    stockService.inStock(userLoginDto.getUserDto().getId(), stockInDto);

            model.addAttribute("stockDto", stockDto);
            return "stock/stockinSuccess";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    private boolean isStringEmpty(String str) {
        return str == null || str.isBlank();
    }
}
