package ge.technicShop.controllers;

import ge.technicShop.dto.CartItemRequest;
import ge.technicShop.dto.CartResponse;
import ge.technicShop.entities.User;
import ge.technicShop.services.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public void add(@AuthenticationPrincipal User user, @RequestBody CartItemRequest request) {
        cartService.addToCart(user, request.getProductId(), request.getQuantity());
    }

    @GetMapping
    public CartResponse getCart(@AuthenticationPrincipal User user) {
        return cartService.getCart(user);
    }

    @DeleteMapping("/remove/{productId}")
    public void removeFromCart(@AuthenticationPrincipal User user, @PathVariable Long productId) {
        cartService.removeFromCart(user, productId);
    }
}