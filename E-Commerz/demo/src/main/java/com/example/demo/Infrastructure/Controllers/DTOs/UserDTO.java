package com.example.demo.Infrastructure.Controllers.DTOs;

import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Address;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Password;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Privilege;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Username;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Getter
@Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO{

    @Setter
    private int userId;

    private Username username;
    private Password password;
    private Address address;
    private Privilege privilege;

    private List<OrderDTO> orders;

}
