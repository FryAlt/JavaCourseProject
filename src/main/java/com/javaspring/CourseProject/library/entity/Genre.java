package com.javaspring.CourseProject.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "genres", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private java.util.Set<Book> books = new java.util.HashSet<>();

    public Genre() {}
    public Genre(String name) { this.name = name; }

    // Геттеры/сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public java.util.Set<Book> getBooks() { return books; }
    public void setBooks(java.util.Set<Book> books) { this.books = books; }
}
