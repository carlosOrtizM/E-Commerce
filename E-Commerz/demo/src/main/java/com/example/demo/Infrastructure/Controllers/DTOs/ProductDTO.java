package com.example.demo.Infrastructure.Controllers.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;


@Component
@Getter
@Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @Setter
    private int productId;

    private String productName;
    private boolean sold;
    private int categoryId;
    private double price;

    private OrderDTO orders;
}
