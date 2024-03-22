package com.example.demo.Infrastructure.Controllers.DTOs.Components.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Password {
    private String password;
}
