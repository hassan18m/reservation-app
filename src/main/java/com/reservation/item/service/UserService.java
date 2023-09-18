package com.reservation.item.service;

import com.reservation.item.entity.User;
import com.reservation.item.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserById(Long id);

    UserDto addUser(User user);

    UserDto updateUser(Long id, User user);

    UserDto deleteUser(Long id);

    void deleteAll();

    UserDto addProductsToUser(Long userId, Long productId);
}
