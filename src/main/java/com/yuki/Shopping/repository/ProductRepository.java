package com.yuki.Shopping.repository;

import com.yuki.Shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product,Long>, QuerydslPredicateExecutor <Product> {
    List <Product> findByProductName(String productName);

    List <Product> findByProductNameOrProductDetail(String productName, String productDetail);

    List <Product> findByPriceLessThan(Integer price);

    List <Product> findByPriceLessThanOrderByPriceAsc(Integer price);

    @Query("select p from Product p where p.productDetail like %:productDetail% order by p.price desc")
    List <Product> findByProductDetail(@Param("productDetail") String productDetail);

    @Query(value = "select * from product p where p.product_detail like %:productDetail% order by p.price desc", nativeQuery = true)
    List <Product> findByProductDetailByNative(@Param("productDetail") String productDetail);

}
