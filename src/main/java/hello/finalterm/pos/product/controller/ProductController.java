package hello.finalterm.pos.product.controller;

import hello.finalterm.pos.product.dto.ProductCreateDto;
import hello.finalterm.pos.product.dto.ProductDto;
import hello.finalterm.pos.product.service.ProductService;
import hello.finalterm.pos.user.dto.UserLoginDto;
import hello.finalterm.pos.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProductController {

    private final ProductService productService;

    private final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/product/add")
    public String productAddForm(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        model.addAttribute("productCreateDto", new ProductCreateDto());

        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        model.addAttribute("user", userLoginDto.getUserDto());

        return "product/productAddForm";
    }

    @PostMapping("/product/add")
    public String productAdd(ProductCreateDto productCreateDto, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        UserLoginDto userLoginDto = userService.isUserLogin(request);

        if (!userLoginDto.getUserLogin()) {
            redirectAttributes.addAttribute("error", userLoginDto.getErrorMessage());
            return "redirect:/errorPage";
        }

        if (productCreateDto == null) {
            redirectAttributes.addAttribute("error", "productCreateDto가 null입니다");
            return "redirect:/errorPage";
        }

        if (isStringEmpty(productCreateDto.getCode())) {
            redirectAttributes.addAttribute("error", "제품 코드가 비어있습니다.");
            return "redirect:/errorPage";
        }

        if (productCreateDto.getPrice() == null) {
            redirectAttributes.addAttribute("error", "제품 가격이 비어있습니다.");
            return "redirect:/errorPage";
        }

        if (isStringEmpty(productCreateDto.getName())) {
            redirectAttributes.addAttribute("error", "제품 이름이 비어있습니다.");
            return "redirect:/errorPage";
        }

        try {
            ProductDto productDto = productService.createProduct(productCreateDto);
            model.addAttribute("productDto", productDto);
            return "product/productAddSuccess";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    private boolean isStringEmpty(String str) {
        return str == null || str.isBlank();
    }
}
