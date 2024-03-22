package com.example.demo.Domain;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Users.Privilege;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Getter @Setter
@Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    private @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") int userId;

    private @Column(name = "username") String username;
    private @Column(name = "password") String password;
    private @Column(name = "address") String address;
    private @NonNull@Column(name = "privilege") String privilege;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Orders> ordersList = new ArrayList<>();

    public void addOrder(Orders orders){
        ordersList.add(orders);
        orders.setUsers(this);
    }
    public void removeOrder(Orders orders){
        ordersList.remove(orders);
        orders.setUsers(null);
    }
}