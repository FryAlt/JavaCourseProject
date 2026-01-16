package com.javaspring.CourseProject.library.controller;

import com.javaspring.CourseProject.library.entity.Book;
import com.javaspring.CourseProject.library.service.BookService;
import com.javaspring.CourseProject.library.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final CartService cartService;

    public BookController(BookService bookService,
                             CartService cartService) {
        this.bookService = bookService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String listBooks(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            Model model) {

        List<Book> books = bookService.filterSearch( title, genre, author, yearFrom, yearTo);
        List<String> genres = bookService.getAllGenres();

        model.addAttribute("books", books);
        model.addAttribute("genre", genre);
        model.addAttribute("yearFrom", yearFrom);
        model.addAttribute("yearTo", yearTo);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("genre", genres);

        return "books/list";
    }

    @GetMapping("/books/{id}")
    public String bookDetail(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "books/detail";
    }
    
}