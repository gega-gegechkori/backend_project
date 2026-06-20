package ge.technicShop.repositories;

import ge.technicShop.entities.Cart;
import ge.technicShop.entities.CartItem;
import ge.technicShop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteByCartAndProduct(Cart cart, Product product);

}