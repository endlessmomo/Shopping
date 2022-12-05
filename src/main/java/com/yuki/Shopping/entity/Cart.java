package com.yuki.Shopping.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "cart")
@Entity
public class Cart {
    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
}
