package com.javaspring.CourseProject.library.service;

import com.javaspring.CourseProject.library.entity.Book;
import com.javaspring.CourseProject.library.entity.Favorite;
import com.javaspring.CourseProject.library.entity.User;
import com.javaspring.CourseProject.library.repository.BookRepository;
import com.javaspring.CourseProject.library.repository.FavoriteRepository;
import com.javaspring.CourseProject.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           UserRepository userRepository,
                           BookRepository bookRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> getFavoriteBooks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return favoriteRepository.findBooksByUser(user); // ← вызываем правильный метод
    }

    public void addToFavorites(String username, Long bookId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + username));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена с ID: " + bookId));

        if (!favoriteRepository.existsByUserIdAndBookId(user.getId(), book.getId())) {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setBook(book);
            favoriteRepository.save(favorite);
        }
    }

    public void deleteFromFavorites(String username, Long bookId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        favoriteRepository.findByUserAndBook(user, book)
                .ifPresent(favoriteRepository::delete);
    }

    public List<Favorite> getFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        return favoriteRepository.findByUser(user);
    }
}