package com.podzirei.springonlineshop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
@Table(name = "products")
public class Product {
    @Id
//    @SequenceGenerator(
//            name = "product_id_seq",
//            sequenceName = "product_id_seq",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "product_id_seq"
//    )
    private int id;

    private String name;
    private double price;
    private LocalDateTime creationDate;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
