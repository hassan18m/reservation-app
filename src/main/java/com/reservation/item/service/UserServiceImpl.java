package com.reservation.item.service;

import com.reservation.item.entity.Product;
import com.reservation.item.entity.User;
import com.reservation.item.exception.NotFoundException;
import com.reservation.item.helper.MapEntity;
import com.reservation.item.model.UserDto;
import com.reservation.item.repository.ProductRepository;
import com.reservation.item.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable("users")
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(MapEntity::mapUserToUserDto)
                .toList();
    }

    @Override
    @Cacheable("user")
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(MapEntity::mapUserToUserDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "user", key = "#user.id")})
    public void addUser(User user) {
        userRepository.save(user);
        MapEntity.mapUserToUserDto(user);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "user", key = "#user.id")})
    public UserDto updateUser(Long id, User user) {
        Optional<User> userFound = userRepository.findById(id);
        if (userFound.isPresent()) {
            User userToUpdate = userFound.get();
            MapEntity.mapUserProperties(userToUpdate, user);
            userRepository.save(userToUpdate);

            return MapEntity.mapUserToUserDto(userToUpdate);
        }
        return null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "user", key = "#id")})
    public UserDto deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        userRepository.deleteById(id);
        return MapEntity.mapUserToUserDto(user);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "user", key = "#userId")})
    public UserDto addProductsToUser(Long userId, Long productId) {
        User foundUser = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);

        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);

        List<Product> userProducts = foundUser.getProducts();
        if (userProducts.contains(foundProduct)) {
            throw new RuntimeException("Product already reserved!");
        }
        userProducts.add(foundProduct);
        foundUser.setProducts(userProducts);
        userRepository.save(foundUser);

        return MapEntity.mapUserToUserDto(foundUser);
    }

    @Override
    public Page<UserDto> findAll(Pageable page) {
        List<UserDto> userDtos = userRepository.findAll(page)
                .stream()
                .map(MapEntity::mapUserToUserDto)
                .toList();

        return new PageImpl<>(userDtos, page, userDtos.size());
    }

    @Override
    public Page<UserDto> findByFirstNameContaining(String firstName, Pageable page) {
        List<UserDto> userDtos = userRepository.findByFirstNameContainingIgnoreCase(firstName, page)
                .stream()
                .map(MapEntity::mapUserToUserDto)
                .toList();

        return new PageImpl<>(userDtos, page, userDtos.size());
    }
}