package com.javaspring.CourseProject.library.controller;

import com.javaspring.CourseProject.library.entity.Cart;
import com.javaspring.CourseProject.library.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        cartService.addToCart(principal.getName(), bookId);
        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getUserCart(principal.getName());

        // сумма заказа = Σ (цена * количество)
        java.math.BigDecimal total = cart.getItems().stream()
                .map(item -> item.getBook().getPrice()
                        .multiply(java.math.BigDecimal.valueOf(item.getQuantity())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", total);

        return "cart/view";
    }

    @PostMapping("/remove/{itemId}")
    public String removeItem(@PathVariable Long itemId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        cartService.removeItem(principal.getName(), itemId);
        return "redirect:/cart";
    }

    @PostMapping("/increase/{itemId}")
    public String increase(@PathVariable Long itemId, Principal principal) {
        if (principal != null) {
            cartService.increaseItem(itemId, principal.getName());
        }
        return "redirect:/cart";
    }

    @PostMapping("/decrease/{itemId}")
    public String decrease(@PathVariable Long itemId, Principal principal) {
        if (principal != null) {
            cartService.decreaseItem(itemId, principal.getName());
        }
        return "redirect:/cart";
    }
    
}