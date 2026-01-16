package com.javaspring.CourseProject.library.controller;

import com.javaspring.CourseProject.library.entity.Order;
import com.javaspring.CourseProject.library.entity.OrderItem;
import com.javaspring.CourseProject.library.service.OrderService;
import com.javaspring.CourseProject.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService,
                           UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createOrder(Principal principal) {
        orderService.createOrder(principal.getName());
        return "redirect:/orders";
    }

    @GetMapping
    public String orderList(Model model, Principal principal) {
        model.addAttribute(
                "orders",
                userService.getOrders(principal.getName())
        );
        return "orders/list";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id,
                              Principal principal,
                              Model model) {

        Order order = orderService.findByIdForUser(id, principal.getName());

        order.getItems().forEach(item -> {
            item.setTotalPrice(
                    item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        });

        BigDecimal orderTotal = order.getItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("order", order);
        model.addAttribute("orderTotal", orderTotal);

        return "orders/detail";
    }

}