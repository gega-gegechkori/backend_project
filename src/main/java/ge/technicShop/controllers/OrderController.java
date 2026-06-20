package ge.technicShop.controllers;

import ge.technicShop.entities.Order;
import ge.technicShop.entities.User;
import ge.technicShop.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController extends BaseController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // მოდიფიცირებული ჩექაუთი, რომელიც იღებს პრომო კოდს (არასავალდებულოს)
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String couponCode) {
        return ResponseEntity.ok(orderService.checkout(user, couponCode));
    }

    // ახალი ფუნქცია: შეკვეთის გაუქმება
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, user));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }
}