package com.javaspring.CourseProject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    // Конструкторы

    public Cart() {}

    public Cart(User user) {
        this.user = user;
    }

    // Геттеры и сеттеры

    public Long getIdCart() { return id; }
    public void setIdCart(Long idCart) { this.id = idCart; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    @Override
    public String toString() {
        return "Cart{" +
                "idCart=" + id +
                ", user=" + user.getUsername() +
                '}';
    }
}