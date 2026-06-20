package ge.technicShop.dto;

public class DiscountDto {
    private Long productId;
    private String productName;
    private Double discountPercentage;
    private Double discountedPrice;


    public DiscountDto() {}


    public DiscountDto(Long productId, String productName, Double discountPercentage, Double discountedPrice) {
        this.productId = productId;
        this.productName = productName;
        this.discountPercentage = discountPercentage;
        this.discountedPrice = discountedPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }


}