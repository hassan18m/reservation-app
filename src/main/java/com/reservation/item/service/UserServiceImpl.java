package com.reservation.item.service;

import com.reservation.item.entity.Product;
import com.reservation.item.entity.User;
import com.reservation.item.helper.MapEntity;
import com.reservation.item.model.UserDto;
import com.reservation.item.repository.ProductRepository;
import com.reservation.item.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserDto userDto = new UserDto();
            MapEntity.mapUserToUserDto(user);
            return userDto;
        }).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            UserDto userDto = new UserDto();
            MapEntity.mapUserToUserDto(foundUser.get());
            return userDto;
        }
        return null;
    }

    @Override
    public UserDto addUser(User user) {
        userRepository.save(user);
        UserDto userDto = new UserDto();
        MapEntity.mapUserToUserDto(user);
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id, User user) {
        Optional<User> userFound = userRepository.findById(id);
        if (userFound.isPresent()) {
            User userToUpdate = userFound.get();
            MapEntity.mapUserProperties(userToUpdate, user);
            userRepository.save(userToUpdate);

            UserDto userDto = new UserDto();
            MapEntity.mapUserToUserDto(userToUpdate);

            return userDto;
        }
        return null;
    }

    @Override
    public UserDto deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            UserDto userDto = new UserDto();
            MapEntity.mapUserToUserDto(user.get());
            return userDto;
        }
        return null;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public UserDto addProductsToUser(Long userId, Long productId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);

        if (user.isPresent() && product.isPresent()) {
            User foundUser = user.get();
            Product foundProduct = product.get();
            List<Product> products = foundUser.getProducts();
            products.add(foundProduct);
            foundUser.setProducts(products);
            userRepository.save(foundUser);
            return MapEntity.mapUserToUserDto(foundUser);
        }

        return null;
    }
}