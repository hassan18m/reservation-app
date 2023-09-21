package com.reservation.item.service;

import com.reservation.item.entity.Product;
import com.reservation.item.exception.NotFoundException;
import com.reservation.item.helper.MapEntity;
import com.reservation.item.model.ProductDto;
import com.reservation.item.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable("products")
    public List<ProductDto> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(MapEntity::mapProductToProductDto)
                .toList();
    }

    @Override
    @Cacheable("product")
    public ProductDto getProductById(Long id) {
        return MapEntity
                .mapProductToProductDto(productRepository.findById(id)
                        .orElseThrow(NotFoundException::new));
    }

    @Override
    public ProductDto addProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new RuntimeException();
        }
        product.setAddedDate(LocalDate.now());
        productRepository.save(product);
        return MapEntity.mapProductToProductDto(product);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "product", key = "#id")})
    public ProductDto updateProduct(Long id, Product product) {
        Product modifyingProduct = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        try {
            MapEntity.mapProductProperties(modifyingProduct, product);
            productRepository.save(modifyingProduct);
            return MapEntity.mapProductToProductDto(modifyingProduct);
        } catch (Exception e) {
            throw new RuntimeException("Name already existent.");
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "product", key = "#id")})
    public ProductDto deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        productRepository.deleteById(id);

        return MapEntity.mapProductToProductDto(product);
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Override
    public List<ProductDto> getTopExpensiveProducts() {
        return productRepository.findTopByOrderByPriceDesc().stream()
                .map(MapEntity::mapProductToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByAddedDateBetween(LocalDate startDate, LocalDate endDate) {
        return productRepository.findByAddedDateBetween(startDate, endDate)
                .stream()
                .map(MapEntity::mapProductToProductDto)
                .toList();
    }
}
