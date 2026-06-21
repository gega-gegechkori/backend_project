package ge.technicShop.controllers;

import ge.technicShop.dto.AddProduct;
import ge.technicShop.dto.ProductShortInfo;
import ge.technicShop.dto.RequestData;
import ge.technicShop.dto.SearchProduct;
import ge.technicShop.entities.Product;
import ge.technicShop.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('product:read')")
    public List<Product> getAllProducts(
            @RequestParam(defaultValue = "price", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String direction) {
        return productService.getAllProducts(sortBy, direction);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('product:read')")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('product:add')")
    public Product add(@RequestBody AddProduct addProduct) throws Exception {
        return productService.saveProduct(addProduct, null);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('product:add')")
    public Product edit(@PathVariable Long id, @RequestBody AddProduct addProduct) throws Exception {
        return productService.saveProduct(addProduct, id);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('product:read')")
    public Page<Product> search(@RequestBody RequestData<SearchProduct> rd) {
        return productService.search(rd.getData(), rd.getPaging());
    }

    @PostMapping("/searchShortInfo")
    @PreAuthorize("hasAuthority('product:read')")
    public ProductShortInfo searchShortInfo(@RequestBody RequestData<SearchProduct> rd) {
        return productService.getProductShortInfo(rd.getData());
    }
}