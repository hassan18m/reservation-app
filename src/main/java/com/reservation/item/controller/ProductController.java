package com.reservation.item.controller;

import com.reservation.item.entity.Product;
import com.reservation.item.exception.NotFoundException;
import com.reservation.item.helper.MapEntity;
import com.reservation.item.model.GetProductsResponse;
import com.reservation.item.model.ProductDto;
import com.reservation.item.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<GetProductsResponse> getProducts() {
        List<ProductDto> products = productService.getProducts();
        int count = products.size();
        long totalCount = productService.getTotalProductsCount();

        return ResponseEntity.ok(new GetProductsResponse(products, count, totalCount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/date")
    public ResponseEntity<List<ProductDto>> productsBetweenDate(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(productService.findByAddedDateBetween(startDate, endDate));
    }

    @GetMapping("/top5")
    public ResponseEntity<List<ProductDto>> getTopExpensiveProducts() {
        return ResponseEntity.ok(productService.getTopExpensiveProducts());
    }

    @GetMapping("/prod-populate")
    public ResponseEntity<Void> productsPopulate() {
        for (int i = 1; i <= 2500; i++) {
            Product product = new Product();
            product.setName("Name" + i);
            product.setDescription("Description" + i);
            product.setPrice(500D);
            product.setQuantity(5);
            productService.addProduct(MapEntity.mapProductToProductDto(product));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productService.addProduct(productDto));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, productDto));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.deleteProduct(id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        productService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
