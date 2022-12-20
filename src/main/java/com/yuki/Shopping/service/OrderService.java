package com.yuki.Shopping.service;

import com.yuki.Shopping.dto.OrderDto;
import com.yuki.Shopping.entity.Member;
import com.yuki.Shopping.entity.Order;
import com.yuki.Shopping.entity.OrderItem;
import com.yuki.Shopping.entity.Product;
import com.yuki.Shopping.repository.MemberRepository;
import com.yuki.Shopping.repository.OrderRepository;
import com.yuki.Shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {
        Product product = productRepository.findById(orderDto.getProductId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List <OrderItem> orderItemList = new ArrayList <>();
        OrderItem orderItem = OrderItem.createOrderItem(product, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}
