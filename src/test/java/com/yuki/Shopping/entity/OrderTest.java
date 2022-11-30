package com.yuki.Shopping.entity;

import com.yuki.Shopping.constant.ProductStatus;
import com.yuki.Shopping.repository.OrderRepository;
import com.yuki.Shopping.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    EntityManager em;



    public Product createItem(){
        Product product = Product.builder()
                .productName("테스트 상품")
                .price(10000)
                .productDetail("상세 설명")
                .productStatus(ProductStatus.SELL)
                .stockCount(100)
                .registered_date(LocalDateTime.now())
                .updated_date(LocalDateTime.now())
                .build();

        productRepository.save(product);
        return product;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    void cascadeTest() {
        //given
        Order order = new Order();

        //when
        for(int i = 0; i < 3; i++){
            Product product = this.createItem();
            productRepository.save(product);
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .count(10)
                    .orderPrice(1000)
                    .order(order)
                    .build();
            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order);
        em.clear();

        //then
        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }
}