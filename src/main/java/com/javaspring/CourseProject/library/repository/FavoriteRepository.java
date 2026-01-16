package com.javaspring.CourseProject.library.repository;

import com.javaspring.CourseProject.library.entity.Book;
import com.javaspring.CourseProject.library.entity.Favorite;
import com.javaspring.CourseProject.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    List<Favorite> findByUser(User user);

    Optional<Favorite> findByUserAndBook(User user, Book book);

    @Query("SELECT f.book FROM Favorite f WHERE f.user = :user")
    List<Book> findBooksByUser(@Param("user") User user);

}