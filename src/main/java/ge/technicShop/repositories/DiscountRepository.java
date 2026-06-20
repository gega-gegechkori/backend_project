package ge.technicShop.repositories;

import ge.technicShop.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> findAllByEndDateAfter(LocalDateTime now);
}