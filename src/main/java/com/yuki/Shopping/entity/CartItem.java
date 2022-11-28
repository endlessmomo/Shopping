package com.yuki.Shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Table(name="cart_id")
@Entity
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name = "cart_item_Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int count;
}
