package ge.technicShop.dto;

import java.util.List;

public class CartResponse {
    private List<CartItemResponse> items;
    private Double totalPrice;

    public CartResponse(List<CartItemResponse> items, Double totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public List<CartItemResponse> getItems() { return items; }
    public void setItems(List<CartItemResponse> items) { this.items = items; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}