package com.javaspring.CourseProject.library.controller;

import com.javaspring.CourseProject.library.entity.Book;
import com.javaspring.CourseProject.library.repository.FavoriteRepository;
import com.javaspring.CourseProject.library.repository.UserRepository;
import com.javaspring.CourseProject.library.service.FavoriteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;;

    public FavoriteController(FavoriteService favoriteService, FavoriteRepository favoriteRepository, UserRepository userRepository) {
        this.favoriteService = favoriteService;

    }

    @PostMapping("/add/{bookId}")
    public String addToFavorites(@PathVariable Long bookId, Principal principal, RedirectAttributes redirectAttrs) {
        System.out.println("Received bookId: " + bookId); // ← смотрите в консоль сервера
            if (principal == null) return "redirect:/login";
        favoriteService.addToFavorites(principal.getName(), bookId);
            return "redirect:/books";
    }

    @PostMapping("/remove/{bookId}")
    public String removeFromFavorites(@PathVariable Long bookId, Principal principal) {
        if (principal == null) return "redirect:/login";
        favoriteService.deleteFromFavorites(principal.getName(), bookId);
        return "redirect:/favorites";
    }

    @GetMapping
    public String favorites(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        List<Book> favoriteBooks = favoriteService.getFavoriteBooks(principal.getName());
        
        model.addAttribute("books", favoriteBooks);
        return "favorites/list";
    }
    
}