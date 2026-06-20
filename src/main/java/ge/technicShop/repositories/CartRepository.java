package ge.technicShop.repositories;

import ge.technicShop.entities.Cart;
import ge.technicShop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
