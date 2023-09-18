package com.reservation.item.service;

import com.reservation.item.entity.Product;
import com.reservation.item.model.ProductDto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts();

    ProductDto getProductById(Long id) throws Exception;

    ProductDto addProduct(Product product);

    ProductDto updateProduct(Long id, Product product);

    ProductDto deleteProduct(Long id);

    void deleteAll();

    List<ProductDto> getProductsByAddedDate();

    List<ProductDto> getTopExpensiveProducts();

    List<ProductDto> findByAddedDateBetween(LocalDate startDate, LocalDate endDate);
}
