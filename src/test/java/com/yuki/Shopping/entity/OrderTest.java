package com.yuki.Shopping.entity;

import com.yuki.Shopping.constant.ProductStatus;
import com.yuki.Shopping.repository.MemberRepository;
import com.yuki.Shopping.repository.OrderItemRepository;
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
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em;


    public Product createItem() {
        Product product = Product.builder()
                .productName("테스트 상품")
                .price(10000)
                .productDetail("상세 설명")
                .productStatus(ProductStatus.SELL)
                .stockCount(100)
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
        for (int i = 0; i < 3; i++) {
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

    public Order createOrder() {
        Order order = new Order();

        for (int i = 0; i < 5; i++) {
            Product product = createItem();
            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .count(10)
                    .orderPrice(1000)
                    .order(order)
                    .build();

            order.getOrderItems().add(orderItem);
        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    void orphanRemovalTest() {
        //given
        Order order = this.createOrder();
        //when
        order.getOrderItems().remove(0);
        //then
        em.flush();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    void lazyLoadingTest(){
        //given
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        //when
        em.flush();
        em.clear();
        //then
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("Order class : " + orderItem.getOrder().getClass());
        System.out.println("===========================");
        orderItem.getOrder().getOrderDate();
        System.out.println("===========================");
    }
}