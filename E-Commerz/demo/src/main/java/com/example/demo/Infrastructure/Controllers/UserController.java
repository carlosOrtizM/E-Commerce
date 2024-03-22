package com.example.demo.Infrastructure.Controllers;

import com.example.demo.Application.UserServiceImpl;
import com.example.demo.Application.UserValidationInjector;
import com.example.demo.Exceptions.AssetException;
import com.example.demo.Infrastructure.Controllers.DTOs.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserValidationInjector userValidationInjector;

    @GetMapping("/users")
    List<UserDTO> getAllUsers(){
        return userServiceImpl.getAllAssets();
    }

    @GetMapping("/users/{user}")
    UserDTO getUsersbyUser(@PathVariable(value = "user")String requestedUser){
        return userServiceImpl.getUserByUser(requestedUser)
                .orElseThrow(()->new AssetException("User not found", AssetException.ErrorCode.USERNOTFOUND, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/users")
    void addUser(@RequestBody UserDTO newUser){
        userValidationInjector.getAssetConsumerValidation().assetCreateValidation(newUser);
    }

    @PutMapping("/users/{user}")
    void updateUser(@PathVariable(value = "user") String requestedUser, @RequestBody UserDTO modifiedUser){
        userValidationInjector.getAssetConsumerValidation().assetUpdateValidation(requestedUser,modifiedUser);
    }

    @DeleteMapping("/users/{userId}")
    void deleteUser(@PathVariable(value = "userId") int requestedUserId){
        userServiceImpl.deleteAssetById(requestedUserId);
    }
}

