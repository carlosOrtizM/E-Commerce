package com.example.demo.Application;

import com.example.demo.Domain.Orders;
import com.example.demo.Domain.Products;
import com.example.demo.Exceptions.AssetException;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Orders.Status;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Privilege;
import com.example.demo.Infrastructure.Controllers.DTOs.OrderDTO;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Address;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Password;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Username;
import com.example.demo.Infrastructure.Controllers.DTOs.ProductDTO;
import com.example.demo.Infrastructure.Controllers.DTOs.UserDTO;
import com.example.demo.Domain.Users;
import com.example.demo.Infrastructure.Ports.AssetLifecycle;
import com.example.demo.Infrastructure.Repositories.OrderRepository;
import com.example.demo.Infrastructure.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements AssetLifecycle<UserDTO> {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrderRepository orderRepository;

    public List<UserDTO> getAllAssets(){
        List<Users> foundEntities = userRepository.findAll();
        return foundEntities.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public Optional<UserDTO> getAssetByName(String name){
        Optional<Users> foundEntity = userRepository.findByUsername(name);
        return foundEntity.map(this::convertToDto);
    }
    public Optional<UserDTO> getAssetById(int id){
        Optional<Users> foundEntity = userRepository.findByUserId(id);
        return foundEntity.map(this::convertToDto);
    }
    public Optional<UserDTO> getUserByUser(String requestedUser){
        if (requestedUser.matches("^[0-9]+$")) {
            return getAssetById(Integer.parseInt(requestedUser));
        }
        else{
            return getAssetByName(requestedUser);
        }
    }

    public void createAsset(UserDTO created){
        userRepository.save(convertToEntity(created));
    }

    public void updateAssetByName(String asset, UserDTO updater){
        Optional<Users> foundEntity = userRepository.findByUsername(asset);
        updateAsset(foundEntity.orElseThrow(() -> new AssetException("Asset not found.", AssetException.ErrorCode.USERNOTFOUND, HttpStatus.NOT_FOUND)), updater);
    }
    public void updateAssetById(int asset, UserDTO updater){
        Optional<Users> foundEntity = userRepository.findByUserId(asset);
        updateAsset(foundEntity.orElseThrow(() -> new AssetException("Asset not found.", AssetException.ErrorCode.USERNOTFOUND, HttpStatus.NOT_FOUND)), updater);
    }
    public void updateAssetByAsset(String requestedUser, UserDTO updater){
        if(requestedUser.matches("^[0-9]+$")){
            updateAssetById(Integer.parseInt(requestedUser),updater);
        }
        else{
            updateAssetByName(requestedUser,updater);
        }
    }
    private void updateAsset(Users foundEntity, UserDTO updater){
        int userId = foundEntity.getUserId();
        List<Orders> iterator = new ArrayList<Orders>(foundEntity.getOrdersList());

        for(Orders x: iterator){
            foundEntity.removeOrder(x);
        }
        for(OrderDTO y: updater.getOrders()){
            foundEntity.addOrder(orderRepository.findByOrderId(y.getOrderId())
                    .orElseThrow(() -> new AssetException("Order not found.", AssetException.ErrorCode.ORDERNOTFOUND, HttpStatus.NOT_FOUND)));
        }

        foundEntity = convertToEntity(updater);
        foundEntity.setUserId(userId);
        userRepository.save(foundEntity);
    }

    public void deleteAssetById(int id){
        try {
            Optional<Users> foundEntity = userRepository.findByUserId(id);
            List<Orders> iterator = new ArrayList<Orders>(foundEntity.get().getOrdersList());

            for(Orders x: iterator){
                foundEntity.get().removeOrder(x);
            }
            userRepository.deleteById(id);
        } catch (NoSuchElementException e){
            throw new AssetException("Asset not found.", AssetException.ErrorCode.USERNOTFOUND, HttpStatus.NOT_FOUND);
        }
    }

    private Users convertToEntity(UserDTO userDTO){
        try{
            return Users.builder()
                    .username(userDTO.getUsername().getUsername())
                    .password(userDTO.getPassword().getPassword())
                    .address(userDTO.getAddress().getCompleteAddress())
                    .privilege(userDTO.getPrivilege().toString())
                    .build();
        }
        catch (ConstraintViolationException | DataIntegrityViolationException |NullPointerException exception){
            throw new AssetException("Bad JSON request.", AssetException.ErrorCode.JSON_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
    private UserDTO convertToDto(Users user){
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(new Username(user.getUsername()))
                .password(new Password(user.getPassword()))
                .address(new Address(user.getAddress()))
                .privilege(Privilege.valueOf(user.getPrivilege()))
                .orders(user.getOrdersList().stream().map(
                                orders1 -> OrderDTO.builder()
                                        .orderId(orders1.getOrderId())
                                        .createdDate(orders1.getCreatedDate())
                                        .status(Status.valueOf(orders1.getStatus()))
                                        .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
