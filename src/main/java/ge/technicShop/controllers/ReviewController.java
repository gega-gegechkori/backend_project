package ge.technicShop.controllers;

import ge.technicShop.dto.ReviewRequest;
import ge.technicShop.entities.Review;
import ge.technicShop.entities.User;
import ge.technicShop.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController extends BaseController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody ReviewRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reviewService.addReview(request, user));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getProductReviews(productId));
    }
}