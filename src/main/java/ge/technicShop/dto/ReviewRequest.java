package ge.technicShop.dto;

public class ReviewRequest {
    private Long productId;
    private Integer rating;
    private String comment;

    // Getter-ები და Setter-ები
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}