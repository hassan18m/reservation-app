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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "product", key = "#productDto.id")})
    public ProductDto addProduct(ProductDto productDto) {
        Product product = MapEntity.mapProductDtoToProduct(productDto);
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
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product modifyingProduct = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        try {
            Product product = MapEntity.mapProductDtoToProduct(productDto);
            MapEntity.mapProductProperties(modifyingProduct, product);
            productRepository.save(modifyingProduct);

            productDto = MapEntity.mapProductToProductDto(modifyingProduct);
            return productDto;
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
        return productRepository.findTop5ByOrderByPriceDesc()
                .stream()
                .map(MapEntity::mapProductToProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> findByAddedDateBetween(LocalDate startDate, LocalDate endDate) {
        return productRepository.findByAddedDateBetween(startDate, endDate)
                .stream()
                .map(MapEntity::mapProductToProductDto)
                .toList();
    }

    @Override
    public Page<ProductDto> findAll(Pageable page) {
        List<ProductDto> productDtos = productRepository.findAll(page)
                .stream()
                .map(MapEntity::mapProductToProductDto)
                .toList();

        return new PageImpl<>(productDtos, page, productDtos.size());
    }

    @Override
    public Page<ProductDto> findByNameContaining(String name, Pageable page) {
        List<ProductDto> productDtos = productRepository.findByNameContainingIgnoreCase(name, page)
                .stream()
                .map(MapEntity::mapProductToProductDto)
                .toList();

        return new PageImpl<>(productDtos, page, productDtos.size());
    }

    @Override
    public long getTotalProductsCount() {
        return productRepository.count();
    }
}
