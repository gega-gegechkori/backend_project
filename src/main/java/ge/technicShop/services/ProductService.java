package ge.technicShop.services;

import ge.technicShop.dto.AddProduct;
import ge.technicShop.dto.Paging;
import ge.technicShop.dto.ProductShortInfo;
import ge.technicShop.dto.SearchProduct;
import ge.technicShop.entities.Category;
import ge.technicShop.entities.Product;
import ge.technicShop.repositories.ProductRepository;
import ge.technicShop.utils.GeneralUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> getAll() { return productRepository.findAll(); }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PRODUCT_NOT_FOUND"));
    }

    public Product saveProduct(AddProduct dto, Long id) throws Exception {
        GeneralUtil.checkRequiredProperties(dto, List.of("name", "price", "categoryId"));
        Product product = (id != null) ? getById(id) : new Product();
        if (id == null) product.setCreatedDate(new Date());

        GeneralUtil.getCopyOf(dto, product);
        product.setCategory(categoryService.getById(dto.getCategoryId()));
        return productRepository.save(product);
    }

    public Page<Product> search(SearchProduct search, Paging paging) {

        Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize());
        return productRepository.search(search.getSearchText(), search.getPriceFrom(), search.getPriceTo(), pageable);
    }

    public ProductShortInfo getProductShortInfo(SearchProduct search) {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Product> res = productRepository.search(search.getSearchText(), search.getPriceFrom(), search.getPriceTo(), pageable);
        if(!res.isEmpty()) {
            Product p = res.getContent().get(0);
            return new ProductShortInfo(p.getName(), p.getPrice(), p.getImageUrl());
        }
        throw new ResourceNotFoundException("PRODUCT_NOT_FOUND");
    }
}