package com.reservation.item.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private LocalDate addedDate;
}
