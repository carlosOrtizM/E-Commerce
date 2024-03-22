package com.example.demo.Infrastructure.Controllers;

import com.example.demo.Application.OrderServiceImpl;
import com.example.demo.Exceptions.AssetException;
import com.example.demo.Infrastructure.Controllers.DTOs.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/orders")
    List<OrderDTO> getAllOrders(){
        return orderServiceImpl.getAllAssets();
    }

    @GetMapping("/orders/{order}")
    OrderDTO getOrdersbyOrder(@PathVariable(value = "order")String requestedOrder){
        return orderServiceImpl.getOrdersByOrder(requestedOrder)
                .orElseThrow(() ->new AssetException("Order not found", AssetException.ErrorCode.ORDERNOTFOUND, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/orders")
    void addOrder(@RequestBody OrderDTO newOrder){
        orderServiceImpl.createAsset(newOrder);
    }

    @PutMapping("/orders/{order}")
    void updateProduct(@PathVariable(value = "order") int order,@RequestBody OrderDTO modifiedOrder){
        orderServiceImpl.updateAssetById(order, modifiedOrder);
    }

    @DeleteMapping("/orders/{orderId}")
    void deleteProduct(@PathVariable(value = "orderId") int requestedOrderId){
        orderServiceImpl.deleteAssetById(requestedOrderId);
    }
}
