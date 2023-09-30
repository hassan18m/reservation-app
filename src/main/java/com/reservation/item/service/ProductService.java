package com.reservation.item.service;

import com.reservation.item.model.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts();

    ProductDto getProductById(Long id);

    ProductDto addProduct(ProductDto productDto);

    ProductDto updateProduct(Long id, ProductDto productDto);

    ProductDto deleteProduct(Long id);

    void deleteAll();

    List<ProductDto> getTopExpensiveProducts();

    List<ProductDto> findByAddedDateBetween(LocalDate startDate, LocalDate endDate);

    Page<ProductDto> findAll(Pageable page);

    Page<ProductDto> findByNameContaining(String name, Pageable page);

    long getTotalProductsCount();
}
