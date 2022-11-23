package com.yuki.Shopping.entity;

import com.yuki.Shopping.constant.ProductStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "product")
@Entity
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length= 50)
    private String productName;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(nullable=false)
    private int stockCount;

    @Column(nullable = false)
    private String productDetail;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    private LocalDateTime registered_date;
    private LocalDateTime updated_date;
}
