package com.javaspring.CourseProject.library.repository;

import com.javaspring.CourseProject.library.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}