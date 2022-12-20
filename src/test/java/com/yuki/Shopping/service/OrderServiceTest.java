package com.yuki.Shopping.service;

import com.yuki.Shopping.constant.ProductStatus;
import com.yuki.Shopping.dto.OrderDto;
import com.yuki.Shopping.entity.Member;
import com.yuki.Shopping.entity.Order;
import com.yuki.Shopping.entity.OrderItem;
import com.yuki.Shopping.entity.Product;
import com.yuki.Shopping.repository.MemberRepository;
import com.yuki.Shopping.repository.OrderRepository;
import com.yuki.Shopping.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    public Product saveProduct() {
        Product product = Product.builder()
                .productName("테스트 상품")
                .price(40000)
                .productDetail("테스트 상품입니다.")
                .productStatus(ProductStatus.SELL)
                .stockCnt(100)
                .build();

        return productRepository.save(product);
    }

    public Member saveMember() {
        Member member = Member.builder()
                .email("soohyuk96@naver.com")
                .build();

        return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문 테스트")
    void order() {
        //given
        Product product = saveProduct();
        Member member = saveMember();
        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setProductId(product.getId());
        //when
        Long orderId = orderService.order(orderDto, member.getEmail());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        List <OrderItem> orderItems = order.getOrderItems();
        int totalPrice = orderDto.getCount() * product.getPrice();
        //then
        assertEquals(totalPrice, order.getTotalPrice());
    }
}