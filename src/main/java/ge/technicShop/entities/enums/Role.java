package ge.technicShop.entities.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

public enum Role {
    SHOP_ADMIN(List.of(Permission.PRODUCT_READ, Permission.PRODUCT_ADD)),
    SHOP_USER(List.of(Permission.PRODUCT_READ));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) { this.permissions = permissions; }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(i -> new SimpleGrantedAuthority(i.getKeyword()))
                .toList();
    }

    public List<Permission> getPermissions() { return permissions; }
}