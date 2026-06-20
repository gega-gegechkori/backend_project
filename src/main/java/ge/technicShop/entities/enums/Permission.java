package ge.technicShop.entities.enums;

public enum Permission {
    PRODUCT_READ("product:read"), PRODUCT_ADD("product:add");

    private final String keyword;

    Permission(String keyword) { this.keyword = keyword; }
    public String getKeyword() { return keyword; }
}