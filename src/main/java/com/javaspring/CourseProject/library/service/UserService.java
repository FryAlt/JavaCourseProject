package com.javaspring.CourseProject.library.service;

import com.javaspring.CourseProject.library.entity.Order;
import com.javaspring.CourseProject.library.entity.User;
import com.javaspring.CourseProject.library.repository.OrderRepository;
import com.javaspring.CourseProject.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository,
                       OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<Order> getOrders(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        return orderRepository.findByUser(user);
    }
}
