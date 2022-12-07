package com.yuki.Shopping.repository;

import com.yuki.Shopping.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    List <ProductImg> findByProductIdOrderByIdAsc(Long productId);
}
