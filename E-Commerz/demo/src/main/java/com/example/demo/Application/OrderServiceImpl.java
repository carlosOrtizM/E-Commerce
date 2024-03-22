package com.example.demo.Application;

import com.example.demo.Domain.Products;
import com.example.demo.Domain.Users;
import com.example.demo.Exceptions.AssetException;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Orders.Status;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Password;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Privilege;
import com.example.demo.Infrastructure.Ports.AssetLifecycle;
import com.example.demo.Infrastructure.Repositories.OrderRepository;
import com.example.demo.Infrastructure.Controllers.DTOs.OrderDTO;
import com.example.demo.Domain.Orders;
import com.example.demo.Infrastructure.Controllers.DTOs.ProductDTO;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Address;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Username;
import com.example.demo.Infrastructure.Controllers.DTOs.UserDTO;
import com.example.demo.Infrastructure.Repositories.ProductRepository;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements AssetLifecycle<OrderDTO>{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<OrderDTO> getAllAssets(){
        List<Orders> foundEntities = orderRepository.findAll();
        return foundEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public Optional<OrderDTO> getAssetByName(String name){
        Optional<List<Orders>> foundEntities = orderRepository.findByStatus(name);
        List<Orders> foundEntities2 = foundEntities.get();
        Optional<Orders> firstFoundEntity = foundEntities2.stream().findAny();
        return firstFoundEntity.map(this::convertToDTO);
    }
    public Optional<OrderDTO> getAssetById(int id){
        Optional<Orders> foundEntity = orderRepository.findByOrderId(id);
        return foundEntity.map(this::convertToDTO);
    }
    public Optional<OrderDTO> getOrdersByOrder(String requestedOrder){
        if(requestedOrder.matches("^[0-9]+$")){
            return getAssetById(Integer.parseInt(requestedOrder));
        } else {
            return getAssetByName(requestedOrder);
        }
    }

    public void createAsset(OrderDTO created){
        orderRepository.save(convertToEntity(created));
    }
    public void updateAssetByName(String asset, OrderDTO updater){

    }
    public void updateAssetById(int asset, OrderDTO updater){
        Optional<Orders> foundEntity = orderRepository.findByOrderId(asset);
        updateAsset(foundEntity.orElseThrow(() -> new AssetException("Asset not found.", AssetException.ErrorCode.ORDERNOTFOUND, HttpStatus.NOT_FOUND)), updater);
    }
    public void updateAssetByAsset(String asset, OrderDTO updater){
        //check for products
        //check for orders

    }
    public void updateAsset(Orders foundEntity, OrderDTO updater){
        int orderId = foundEntity.getOrderId();
        List<Products> iterator = new ArrayList<Products>(foundEntity.getProductsLists());

        for(Products x: iterator){
            foundEntity.removeProduct(x);
        }
        for(ProductDTO y: updater.getProducts()){
            foundEntity.addProduct(productRepository.findByProductName(y.getProductName())
                    .orElseThrow(() -> new AssetException("Product not found.", AssetException.ErrorCode.PRODUCTNOTFOUND, HttpStatus.NOT_FOUND)));
        }

        foundEntity = convertToEntity(updater);
        foundEntity.setOrderId(orderId);
        orderRepository.save(foundEntity);
    }

    public void deleteAssetById(int id){
        try {
            Optional<Orders> foundEntity = orderRepository.findByOrderId(id);
            List<Products> iterator = new ArrayList<Products>(foundEntity.get().getProductsLists());

            for(Products x: iterator){
                foundEntity.get().removeProduct(x);
            }
            orderRepository.deleteById(id);
        } catch (NullPointerException|NoSuchElementException e){
            throw new AssetException("Order not found.", AssetException.ErrorCode.ORDERNOTFOUND, HttpStatus.BAD_REQUEST);
        }
    }

    public Orders convertToEntity(OrderDTO orderDTO){
        try{
            Orders orders = Orders.builder()
                    .status(orderDTO.getStatus().toString())
                    .createdDate(orderDTO.getCreatedDate())
                    .build();
            return orders;
        } catch (HttpMessageNotReadableException e){
            throw new AssetException("Bad JSON request.", AssetException.ErrorCode.JSON_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
    public OrderDTO convertToDTO(Orders orders){
        return OrderDTO.builder()
                .orderId(orders.getOrderId())
                .status(Status.valueOf(orders.getStatus()))
                .createdDate(orders.getCreatedDate())
                .products(orders.getProductsLists().stream().map(
                        product -> ProductDTO.builder()
                                        .productId(product.getProductId())
                                        .productName(product.getProductName())
                                        .sold(product.isSold())
                                        .price(product.getPrice())
                                        .build()
                ).collect(Collectors.toList()))
                .users(checkNullUser(orders.getUsers()))
                .build();
    }
    private UserDTO checkNullUser(Users user){
        UserDTO newUser;
        if(Objects.isNull(user)){
            newUser = null;
        } else {
            newUser = UserDTO.builder()
                    .userId(user.getUserId())
                    .username(new Username(user.getUsername()))
                    .address(new Address(user.getAddress()))
                    .build();
            }
        return newUser;
    }
}
