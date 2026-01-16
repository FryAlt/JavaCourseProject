package com.javaspring.CourseProject.library.service;

import com.javaspring.CourseProject.library.entity.Book;
import com.javaspring.CourseProject.library.entity.Cart;
import com.javaspring.CourseProject.library.entity.CartItem;
import com.javaspring.CourseProject.library.entity.User;
import com.javaspring.CourseProject.library.repository.BookRepository;
import com.javaspring.CourseProject.library.repository.CartRepository;
import com.javaspring.CourseProject.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       BookRepository bookRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Cart getUserCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + username));

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Transactional
    public void addToCart(String username, Long bookId) {
        Cart cart = getUserCart(username);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Товар не найден: id=" + bookId));

        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getBook().getId().equals(bookId))
                .findFirst()
                .orElse(null);

        if (existingItem == null) {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setBook(book);
            item.setQuantity(1);
            cart.getItems().add(item);
        } else {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(String username, Long cartItemId) {
        Cart cart = getUserCart(username);
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(String username) {
        Cart cart = getUserCart(username);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    public void increaseItem(Long itemId, String username) {
        Cart cart = getUserCart(username);
        cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(item.getQuantity() + 1);
                    cartRepository.save(cart);
                });
    }

    public void decreaseItem(Long itemId, String username) {
        Cart cart = getUserCart(username);
        cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> {
                    int q = item.getQuantity() - 1;
                    if (q <= 0) {
                        cart.getItems().remove(item);
                    } else {
                        item.setQuantity(q);
                    }
                    cartRepository.save(cart);
                });
    }
}