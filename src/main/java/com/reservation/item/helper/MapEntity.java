package com.reservation.item.helper;

import com.reservation.item.entity.Product;
import com.reservation.item.entity.User;
import com.reservation.item.model.ProductDto;
import com.reservation.item.model.UserDto;

public class MapEntity {

    public static void mapUserProperties(User initialUser, User user) {
        initialUser.setFirstName(user.getFirstName());
        initialUser.setLastName(user.getLastName());
        initialUser.setEmail(user.getEmail());
        initialUser.setPassword(user.getPassword());
    }

    public static UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    public static void mapUserDtoToUser(UserDto userDto, User user) {
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
    }

    public static void mapProductProperties(Product initialProduct, Product product) {
        initialProduct.setName(product.getName());
        initialProduct.setDescription(product.getDescription());
        initialProduct.setPrice(product.getPrice());
        initialProduct.setQuantity(product.getQuantity());
    }

    public static ProductDto mapProductToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setAddedDate(product.getAddedDate());

        return productDto;
    }

    public static void mapProductDtoToProduct(ProductDto productDto, Product product) {
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setAddedDate(productDto.getAddedDate());
    }
}
