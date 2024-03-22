package com.example.demo.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="products")
@Getter @Setter
@Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    private @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") int productId;

    private @Column(name = "price") double price;
    private @Column(name = "name") String productName;
    private @Column(name = "sold") boolean sold;
    private @Column(name = "category_id") int categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Orders orders;
}
