package com.example.demo.Application;

import com.example.demo.Domain.Orders;
import com.example.demo.Exceptions.AssetException;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Orders.Status;
import com.example.demo.Infrastructure.Controllers.DTOs.OrderDTO;
import com.example.demo.Infrastructure.Ports.AssetLifecycle;
import com.example.demo.Infrastructure.Repositories.ProductRepository;
import com.example.demo.Infrastructure.Controllers.DTOs.ProductDTO;
import com.example.demo.Domain.Products;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements AssetLifecycle<ProductDTO>{

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllAssets(){
        List<Products> foundEntities = productRepository.findAll();
        return foundEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public Optional<ProductDTO> getAssetByName(String name){
        Optional<Products> foundEntity = productRepository.findByProductName(name);
        return foundEntity.map(this::convertToDTO);
    }
    public Optional<ProductDTO> getAssetById(int id){
        Optional<Products> foundEntity = productRepository.findByProductId(id);
        return foundEntity.map(this::convertToDTO);
    }
    public Optional<ProductDTO> getProductsByProduct(String requestedProduct){
        if(requestedProduct.matches("^[0-9]+$")){
            return getAssetById(Integer.parseInt(requestedProduct));
        }
        else{
            return getAssetByName(requestedProduct);
        }
    }

    public void createAsset(ProductDTO created){
        try {
            productRepository.save(convertToEntity(created));
        } catch (ConstraintViolationException|DataIntegrityViolationException e){
            throw new AssetException("Bad JSON request.", AssetException.ErrorCode.JSON_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    public void updateAssetByName(String asset, ProductDTO updater){
        Optional<Products> foundEntity = productRepository.findByProductName(asset);
        updateAsset(foundEntity.orElseThrow(() -> new AssetException("Asset not found.", AssetException.ErrorCode.PRODUCTNOTFOUND, HttpStatus.NOT_FOUND)), updater);
    }
    public void updateAssetById(int asset, ProductDTO updater){
        Optional<Products> foundEntity = productRepository.findByProductId(asset);
        updateAsset(foundEntity.orElseThrow(() -> new AssetException("Asset not found.", AssetException.ErrorCode.PRODUCTNOTFOUND, HttpStatus.NOT_FOUND)), updater);
    }
    public void updateAssetByAsset(String requestedProduct, ProductDTO updater){
        if(requestedProduct.matches("^[0-9]+$")){
            updateAssetById(Integer.parseInt(requestedProduct),updater);
        }
        else{
            updateAssetByName(requestedProduct, updater);
        }
    }
    private void updateAsset(Products foundEntity, ProductDTO updater){
        try{
            int productId = foundEntity.getProductId();
            foundEntity = convertToEntity(updater);
            foundEntity.setProductId(productId);
            productRepository.save(foundEntity);
        } catch (ConstraintViolationException|DataIntegrityViolationException exception){
            throw new AssetException("Bad JSON request.", AssetException.ErrorCode.JSON_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteAssetById(int id){
        productRepository.findByProductId(id).orElseThrow(()->new AssetException("Product not found.", AssetException.ErrorCode.PRODUCTNOTFOUND, HttpStatus.NOT_FOUND));
        productRepository.deleteById(id);
    }

    public Products convertToEntity(ProductDTO productDTO){
        try{
            return Products.builder()
                    .productName(productDTO.getProductName())
                    .sold(productDTO.isSold())
                    .categoryId(productDTO.getCategoryId())
                    .price(productDTO.getPrice())
                    .build();
        } catch (ConstraintViolationException|DataIntegrityViolationException e){
            throw new AssetException("Bad JSON request.", AssetException.ErrorCode.JSON_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
    public ProductDTO convertToDTO(Products products){
        return ProductDTO.builder()
                .productId(products.getProductId())
                .productName(products.getProductName())
                .categoryId(products.getCategoryId())
                .sold(products.isSold())
                .price(products.getPrice())
                .orders(checkNullOrder(products.getOrders()))
                .build();
    }
    private OrderDTO checkNullOrder(Orders orders){
        OrderDTO newOrder;
        if(Objects.isNull(orders)){
            newOrder = null;
        } else{
            newOrder = OrderDTO.builder()
                    .orderId(orders.getOrderId())
                    .createdDate(orders.getCreatedDate())
                    .status(Status.valueOf(orders.getStatus()))
                    .build();
        }
        return newOrder;
    }
}
