package ge.technicShop.services;

import ge.technicShop.entities.*;
import ge.technicShop.entities.enums.OrderStatus;
import ge.technicShop.repositories.OrderRepository;
import ge.technicShop.repositories.CartRepository;
import ge.technicShop.repositories.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CouponRepository couponRepository; // ჩავამატეთ კუპონების რეპოზიტორი

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.couponRepository = couponRepository;
    }

    @Transactional
    public Order checkout(User user, String couponCode) {
        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Your cart is empty!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderItems(new ArrayList<>());

        double total = 0;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            order.getOrderItems().add(orderItem);
            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        // პრომო კოდის ვალიდაცია და ფასდაკლების გამოყენება
        if (couponCode != null && !couponCode.trim().isEmpty()) {
            Optional<Coupon> couponOpt = couponRepository.findByCodeAndActiveTrue(couponCode.toUpperCase());
            if (couponOpt.isPresent()) {
                Coupon coupon = couponOpt.get();
                if (coupon.getExpiryDate().isAfter(LocalDateTime.now())) {
                    double discount = total * (coupon.getDiscountPercentage() / 100);
                    total = total - discount;
                } else {
                    throw new RuntimeException("This coupon has expired!");
                }
            } else {
                throw new RuntimeException("Invalid promo code!");
            }
        }

        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    // შეკვეთის გაუქმების ლოგიკა
    @Transactional
    public Order cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        // უსაფრთხოების შემოწმება: ეკუთვნის თუ არა ეს შეკვეთა ამ იუზერს
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to cancel this order!");
        }

        // სტატუსის შემოწმება: გაუქმება შეიძლება მხოლოდ PENDING-ის დროს
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order cannot be canceled because it is already " + order.getStatus());
        }

        order.setStatus(OrderStatus.CANCELED); // დარწმუნდი, რომ OrderStatus ენამში გაქვს CANCELED, თუ არა და დაამატე
        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }
}