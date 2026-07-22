package com.saurabh.ecommerce.controller;
import com.saurabh.ecommerce.service.WishlistService;
import java.util.List;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.CartService;
import com.saurabh.ecommerce.service.CategoryService;
import com.saurabh.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final WishlistService wishlistService;
    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             CartService cartService,
                             WishlistService wishlistService) {

        this.productService = productService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public String viewProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String sort,
            Model model) {


        List<Product> products;


        // Search
        if (keyword != null && !keyword.trim().isEmpty()) {

            products = productService.searchProducts(keyword);

        }

        // Category Filter
        else if (category != null) {

            products = productService.getProductsByCategory(category);

        }

        // Sorting
        else if (sort != null && !sort.isEmpty()) {

            products = productService.getSortedProducts(sort);

        }

        // Pagination
        else {

            Page<Product> productPage =
                    productService.getProductsByPage(page);

            products = productPage.getContent();


            model.addAttribute(
                    "currentPage",
                    page
            );


            model.addAttribute(
                    "totalPages",
                    productPage.getTotalPages()
            );

        }


        model.addAttribute(
                "products",
                products
        );


        model.addAttribute(
                "categories",
                categoryService.getAllCategories()
        );


        model.addAttribute(
                "keyword",
                keyword
        );


        model.addAttribute(
                "selectedCategory",
                category
        );


        model.addAttribute(
                "selectedSort",
                sort
        );


        model.addAttribute(
                "cartCount",
                cartService.getCartCount()
        );


        // Load wishlist products
        List<Long> wishlistIds =
                wishlistService.getMyWishlist()
                        .stream()
                        .map(item -> item.getProduct().getId())
                        .toList();


        model.addAttribute(
                "wishlistIds",
                wishlistIds
        );


        return "index";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id,
                              Model model) {

        Product product =
                productService.getProductById(id);

        model.addAttribute("product", product);

        model.addAttribute("cartCount",
                cartService.getCartCount());

        return "product-details";
    }
    @GetMapping("/buy/{id}")
    public String buyNow(@PathVariable Long id,
                         Model model) {

        Product product =
                productService.getProductById(id);

        model.addAttribute("product", product);

        model.addAttribute("order",
                new com.saurabh.ecommerce.entity.Order());

        model.addAttribute("buyNowPrice",
                product.getPrice());

        return "buy-now";
    }
}