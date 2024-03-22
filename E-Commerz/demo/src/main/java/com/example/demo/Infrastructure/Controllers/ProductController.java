package com.example.demo.Infrastructure.Controllers;

import com.example.demo.Application.ProductServiceImpl;
import com.example.demo.Exceptions.AssetException;
import com.example.demo.Infrastructure.Controllers.DTOs.ProductDTO;
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
public class ProductController {
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping("/products")
    List<ProductDTO> getProducts(){
        return productServiceImpl.getAllAssets();
    }

    @GetMapping("/products/{product}")
    ProductDTO getProductsbyProduct(@PathVariable(value = "product") String requestedProduct){
        return productServiceImpl.getProductsByProduct(requestedProduct)
                .orElseThrow(()->new AssetException("Product not found.", AssetException.ErrorCode.PRODUCTNOTFOUND, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/products")
    void createProduct(@RequestBody ProductDTO newProduct){
        productServiceImpl.createAsset(newProduct);
    }

    @PutMapping("/products/{product}")
    void updateProduct(@PathVariable(value = "product") String requestedProduct, @RequestBody ProductDTO modifiedProduct){
        productServiceImpl.updateAssetByAsset(requestedProduct, modifiedProduct);
    }

    @DeleteMapping("/products/{productId}")
    void deleteProduct(@PathVariable(value = "productId") int requestedProductId){
        productServiceImpl.deleteAssetById(requestedProductId);
    }
}
