package ge.technicShop.controllers;

import ge.technicShop.dto.DiscountDto;
import ge.technicShop.dto.DiscountRequest;
import ge.technicShop.entities.Discount;
import ge.technicShop.services.DiscountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) { this.discountService = discountService; }

    @GetMapping("/active")
    public List<DiscountDto> getActive() {
        return discountService.getActiveDiscounts();
    }

    @PostMapping("/add")
    public DiscountDto addDiscount(@RequestBody DiscountRequest request) {
        Discount savedDiscount = discountService.saveDiscount(request);

        Double originalPrice = savedDiscount.getProduct().getPrice();
        Double discountedPrice = originalPrice - (originalPrice * savedDiscount.getDiscountPercentage() / 100);

        return new DiscountDto(
                savedDiscount.getProduct().getId(),
                savedDiscount.getProduct().getName(),
                savedDiscount.getDiscountPercentage(),
                discountedPrice
        );
    }
}