package com.javaspring.CourseProject.library.repository;

import com.javaspring.CourseProject.library.entity.Cart;
import com.javaspring.CourseProject.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}
