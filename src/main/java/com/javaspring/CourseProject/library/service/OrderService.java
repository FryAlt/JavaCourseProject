package com.javaspring.CourseProject.library.service;

import com.javaspring.CourseProject.library.entity.Cart;
import com.javaspring.CourseProject.library.entity.Order;
import com.javaspring.CourseProject.library.entity.OrderItem;
import com.javaspring.CourseProject.library.entity.User;
import com.javaspring.CourseProject.library.repository.OrderItemRepository;
import com.javaspring.CourseProject.library.repository.OrderRepository;
import com.javaspring.CourseProject.library.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        UserRepository userRepository,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    public Order createOrder(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartService.getUserCart(username);

        Order order = new Order();
        order.setUser(user);
        order = orderRepository.save(order);

        Order finalOrder = order;
        cart.getItems().forEach(cartItem -> {
            OrderItem item = new OrderItem();
            item.setOrder(finalOrder);
            item.setBook(cartItem.getBook());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getBook().getPrice());
            orderItemRepository.save(item);
        });

        cartService.clearCart(username);

        return order;
    }

    public Order findByIdForUser(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied to order " + id);
        }

        return order;
    }

}