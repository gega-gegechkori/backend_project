package ge.technicShop.services;

import ge.technicShop.entities.*;
import ge.technicShop.entities.enums.OrderStatus;
import ge.technicShop.repositories.OrderRepository;
import ge.technicShop.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Order checkout(User user) {
        Cart cart = user.getCart();
        // აქ შევცვალეთ .getItems()
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Your cart is empty!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderItems(new ArrayList<>());

        double total = 0;

        // აქაც შევცვალეთ .getItems()
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            order.getOrderItems().add(orderItem);
            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);

        // აქაც შევცვალეთ .getItems()
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }
}