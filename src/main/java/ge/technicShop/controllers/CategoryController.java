package ge.technicShop.controllers;

import ge.technicShop.entities.Category;
import ge.technicShop.services.CategoryService;
import ge.technicShop.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/all")
    public List<Category> getAll() { return categoryService.getAll(); }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) { return categoryService.getById(id); }

    @PostMapping("/add")
    public Category add(@RequestBody Category category) {
        return categoryRepository.save(category);
    }
}