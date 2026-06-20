package ge.technicShop.services;

import ge.technicShop.dto.DiscountDto;
import ge.technicShop.dto.DiscountRequest;
import ge.technicShop.entities.Discount;
import ge.technicShop.entities.Product;
import ge.technicShop.repositories.DiscountRepository;
import ge.technicShop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public DiscountService(DiscountRepository discountRepository, ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
    }

    public List<DiscountDto> getActiveDiscounts() {
        return discountRepository.findAllByEndDateAfter(LocalDateTime.now()).stream()
                .map(d -> {
                    Double originalPrice = d.getProduct().getPrice();
                    Double discountedPrice = originalPrice - (originalPrice * d.getDiscountPercentage() / 100);

                    return new DiscountDto(
                            d.getProduct().getId(),
                            d.getProduct().getName(),
                            d.getDiscountPercentage(),
                            discountedPrice
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Discount saveDiscount(DiscountRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Discount discount = new Discount();
        discount.setProduct(product);
        discount.setDiscountPercentage(request.getDiscountPercentage());
        discount.setEndDate(LocalDateTime.parse(request.getEndDate()));

        return discountRepository.save(discount);
    }
}