package com.yuki.Shopping.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuki.Shopping.constant.ProductStatus;
import com.yuki.Shopping.entity.Product;
import com.yuki.Shopping.entity.QProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 저장 테스트")
    void createItemTest() {
        //given
        for (int i = 1; i < 10; i++) {
            Product product = Product.builder()
                    .productName("테스트 상품 " + i)
                    .price(10000 + i).productDetail("테스트 상품 상세 설명 " + i)
                    .productStatus(ProductStatus.SELL)
                    .stockCnt(100)
                    .build();
            productRepository.save(product);
        }
    }

    @Test
    @DisplayName("상품 저장 테스트 2")
    void createItemTest2() {
        //given

        IntStream.range(1,6).forEach(i -> {
            Product product = Product.builder()
                    .productName("테스트 상품 " + i)
                    .price(10000 + i).productDetail("테스트 상품 상세 설명 " + i)
                    .productStatus(ProductStatus.SELL)
                    .stockCnt(100)
                    .build();

            productRepository.save(product);
        });

        for (int i = 1; i <= 5; i++) {
            Product product = Product.builder()
                    .productName("테스트 상품 " + i)
                    .price(10000 + i).productDetail("테스트 상품 상세 설명 " + i)
                    .productStatus(ProductStatus.SELL)
                    .stockCnt(100)
                    .build();
            productRepository.save(product);
        }

        for (int i = 6; i <= 10; i++) {
            Product product = Product.builder()
                    .productName("테스트 상품 " + i)
                    .price(10000 + i).productDetail("테스트 상품 상세 설명 " + i)
                    .productStatus(ProductStatus.SOLD_OUT)
                    .stockCnt(100)
                    .build();
            productRepository.save(product);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    void findByProductTest() {
        //given
        this.createItemTest();
        List <Product> productList = productRepository.findByProductName("테스트 상품 1");
        //when
        //then
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세 설명 or 테스트")
    void findByProductNameOrProductDetailTest() {
        //given
        this.createItemTest();
        List <Product> productList =
                productRepository.findByProductNameOrProductDetail(
                        "테스트 상품 1", "테스트 상품 상세 설명 5"
                );
        //when
        //then
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    void findByPriceLessThanTest() {
        //given
        this.createItemTest();
        List <Product> productList
                = productRepository.findByPriceLessThan(100000);
        //when
        //then
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan OrderBy ASC 테스트")
    void findByPriceLessThanOrderByPriceAscTest() {
        //given
        this.createItemTest();
        List <Product> productList
                = productRepository.findByPriceLessThanOrderByPriceAsc(10005);
        //when
        //then
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    void findByProductDetailTest() {
        //given
        this.createItemTest();
        List <Product> productList
                = productRepository.findByProductDetail("테스트 상품 상세 설명");
        //when
        //then
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    @Test
    @DisplayName("nativeQuery를 이용한 상품 조회 테스트")
    void findByProductDetailByNativeTest() {
        //given
        this.createItemTest();
        List <Product> productList
                = productRepository.findByProductDetailByNative("테스트 상품 상세 설명");
        //when
        //then
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회테스트1")
    void queryDslTest() {
        //given
        this.createItemTest2();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct qProduct = QProduct.product;
        JPAQuery <Product> query = queryFactory.selectFrom(qProduct)
                .where(qProduct.productStatus.eq(ProductStatus.SELL))
                .where(qProduct.productDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qProduct.price.desc());

        List <Product> productList = query.fetch();

        //then
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회테스트 2")
    void queryDslTest2() {
        //given
        this.createItemTest2();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QProduct product = QProduct.product;

        String productDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String productStatus = "SELL";

        //when
        booleanBuilder.and(product.productDetail.like("%" + productDetail + "%"));
        booleanBuilder.and(product.price.gt(price));

        if (StringUtils.equals(productStatus, ProductStatus.SELL)) {
            booleanBuilder.and(product.productStatus.eq(ProductStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page <Product> productPagingRequest =
                productRepository.findAll(booleanBuilder, pageable);
        productRepository.findAll(booleanBuilder, pageable);

        //then
        List <Product> resultProductList = productPagingRequest.getContent();
        for(Product resultProduct : resultProductList){
            System.out.println(resultProduct.toString());
        }
    }


}