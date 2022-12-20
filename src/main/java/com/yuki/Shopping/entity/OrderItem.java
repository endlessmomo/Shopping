package com.yuki.Shopping.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    public static OrderItem createOrderItem(Product product, int count) {
        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .count(count)
                .orderPrice(product.getPrice())
                .build();

        product.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice * count;
    }
}
