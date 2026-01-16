package com.javaspring.CourseProject.library.repository;

import com.javaspring.CourseProject.library.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}