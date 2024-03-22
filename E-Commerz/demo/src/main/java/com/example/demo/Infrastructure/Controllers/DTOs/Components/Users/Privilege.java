package com.example.demo.Infrastructure.Controllers.DTOs.Components.Users;

import jakarta.persistence.Enumerated;
import org.springframework.stereotype.Component;

import java.util.Objects;

public enum Privilege {
    ADMIN,
    USER,
    SELLER
}


