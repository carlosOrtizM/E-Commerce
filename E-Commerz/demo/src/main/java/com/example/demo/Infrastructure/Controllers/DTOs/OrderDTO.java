package com.example.demo.Infrastructure.Controllers.DTOs;

import com.example.demo.Infrastructure.Controllers.DTOs.Components.Orders.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
@Getter
@Builder @ToString
@AllArgsConstructor
public class OrderDTO {

    private int orderId;
    private Date createdDate;

    private Status status;

    private List<ProductDTO> products;
    private UserDTO users;

    public OrderDTO(){
        createdDate = Date.from(Instant.now());
    }

}
