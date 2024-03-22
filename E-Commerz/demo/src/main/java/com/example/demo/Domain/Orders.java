package com.example.demo.Domain;

import com.example.demo.Infrastructure.Controllers.DTOs.Components.Orders.Status;
import com.example.demo.Infrastructure.Controllers.DTOs.OrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.UnaryOperator;

@Entity
@Table(name = "orders")
@Getter @Setter
@Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    private @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") int orderId;

    private @NonNull@Column(name = "status") String status;

    private @Column(name = "created_date") Date createdDate;

    @OneToMany(mappedBy = "orders")
    @JsonIgnore
    private List<Products> productsLists = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Users users;

    public void addProduct(Products products){
        productsLists.add(products);
        products.setOrders(this);
    }
    public void removeProduct(Products products){
        productsLists.remove(products);
        products.setOrders(null);
    }
}
