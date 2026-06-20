package ge.technicShop.dto;

public class DiscountRequest {

    private Long productId;
    private Double discountPercentage;
    private String endDate;


    public DiscountRequest() {}


    public DiscountRequest(Long productId, Double discountPercentage, String endDate) {
        this.productId = productId;
        this.discountPercentage = discountPercentage;
        this.endDate = endDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}