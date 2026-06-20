package ge.technicShop.services;

import ge.technicShop.dto.ReviewRequest;
import ge.technicShop.entities.Product;
import ge.technicShop.entities.Review;
import ge.technicShop.entities.User;
import ge.technicShop.repositories.ProductRepository;
import ge.technicShop.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    // Constructor Injection
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    public Review addReview(ReviewRequest request, User user) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (reviewRepository.existsByUserIdAndProductId(user.getId(), product.getId())) {
            throw new RuntimeException("You have already reviewed this product!");
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        // შექმნა ახალი რევიუსი კონსტრუქტორით
        Review review = new Review(
                user,
                product,
                request.getRating(),
                request.getComment(),
                LocalDateTime.now()
        );

        return reviewRepository.save(review);
    }

    public List<Review> getProductReviews(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}