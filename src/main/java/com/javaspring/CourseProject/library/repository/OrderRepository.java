package com.javaspring.CourseProject.library.repository;

import com.javaspring.CourseProject.library.entity.Order;
import com.javaspring.CourseProject.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
