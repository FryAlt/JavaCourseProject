package com.javaspring.CourseProject.library.service;


import com.javaspring.CourseProject.library.entity.Book;
import com.javaspring.CourseProject.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> filterSearch(String title, String genre, String author,  Integer yearFrom, Integer yearTo){
        return bookRepository.findAll().stream()
                .filter(book -> {
                    if (author == null || author.isBlank()) {
                        return true;
                    }
                    String bookAuthor = book.getAuthor();
                    return bookAuthor != null &&
                            bookAuthor.toLowerCase().contains(author.toLowerCase());
                })

                .filter(b -> genre == null || genre.isEmpty()
                        || (b.getGenre() != null
                        && b.getGenre().equalsIgnoreCase(genre)))

                .filter(b -> yearFrom == null || (b.getYear() != null && b.getYear() >= yearFrom))
                .filter(b -> yearTo == null || (b.getYear() != null && b.getYear() <= yearTo))

                .filter(b -> title == null || title.isBlank()
                        || b.getTitle().toLowerCase().contains(title.toLowerCase()))

                .toList();
    }

    public List<String> getAllGenres() {
        return bookRepository.findDifferentGenres();
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + id));
    }

    public Book save(Book product) {
        return bookRepository.save(product);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}