package ge.technicShop.services;

import ge.technicShop.dto.CartItemResponse;
import ge.technicShop.dto.CartResponse;
import ge.technicShop.entities.*;
import ge.technicShop.repositories.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void addToCart(User user, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart(user);
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("PRODUCT_NOT_FOUND"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem(cart, product, 0));

        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
    }

    public CartResponse getCart(User user) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("CART_NOT_FOUND"));

        List<CartItemResponse> items = cart.getItems().stream()
                .map(i -> new CartItemResponse(i.getProduct().getId(), i.getProduct().getName(), i.getProduct().getPrice(), i.getQuantity()))
                .collect(Collectors.toList());

        Double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        return new CartResponse(items, total);
    }

    @Transactional
    public void removeFromCart(User user, Long productId) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("CART_NOT_FOUND"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("PRODUCT_NOT_FOUND"));
        cartItemRepository.deleteByCartAndProduct(cart, product);
    }
}