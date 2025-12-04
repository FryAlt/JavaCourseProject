package com.javaspring.CourseProject.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items", uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "book_id"}))
@IdClass(OrderItem.OrderItemId.class)
public class OrderItem {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "price_at_order", precision = 10, scale = 2, nullable = false)
    private BigDecimal priceAtOrder;

    // Composite ID
    public static class OrderItemId implements java.io.Serializable {
        private Long order;
        private Long book;

        // Required by JPA
        public OrderItemId() {}
        public OrderItemId(Long order, Long book) {
            this.order = order;
            this.book = book;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderItemId that = (OrderItemId) o;
            return order.equals(that.order) && book.equals(that.book);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(order, book);
        }
    }

    // Конструкторы
    public OrderItem() {}
    public OrderItem(Order order, Book book, Integer quantity, BigDecimal priceAtOrder) {
        this.order = order;
        this.book = book;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    // Геттеры/сеттеры
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPriceAtOrder() { return priceAtOrder; }
    public void setPriceAtOrder(BigDecimal priceAtOrder) { this.priceAtOrder = priceAtOrder; }
}