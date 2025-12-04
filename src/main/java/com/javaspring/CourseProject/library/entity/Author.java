package com.javaspring.CourseProject.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Lob
    private String bio;

    // Связи
    @ManyToMany(mappedBy = "authors")
    private java.util.Set<Book> books = new java.util.HashSet<>();

    // Конструкторы
    public Author() {}
    public Author(String fullName) { this.fullName = fullName; }

    // Геттеры/сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public java.util.Set<Book> getBooks() { return books; }
    public void setBooks(java.util.Set<Book> books) { this.books = books; }
}