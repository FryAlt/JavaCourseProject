package com.javaspring.CourseProject.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Для гостей
    @Column(name = "session_id", length = 128)
    private String sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal priceAtAdd;

    @CreationTimestamp
    @Column(name = "added_at", updatable = false)
    private LocalDateTime addedAt;

    // Валидация
    @PrePersist
    @PreUpdate
    protected void validate() {
        if (user == null && sessionId == null) {
            throw new IllegalStateException("Either user or sessionId must be set");
        }
        if (user != null && sessionId != null) {
            throw new IllegalStateException("Only one of user or sessionId may be set");
        }
    }

    // Конструкторы
    public Cart() {}

    public Cart(User user, Book book, Integer quantity, BigDecimal priceAtAdd) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.priceAtAdd = priceAtAdd;
    }

    public Cart(String sessionId, Book book, Integer quantity, BigDecimal priceAtAdd) {
        this.sessionId = sessionId;
        this.book = book;
        this.quantity = quantity;
        this.priceAtAdd = priceAtAdd;
    }

    // Геттеры/сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPriceAtAdd() { return priceAtAdd; }
    public void setPriceAtAdd(BigDecimal priceAtAdd) { this.priceAtAdd = priceAtAdd; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
}
