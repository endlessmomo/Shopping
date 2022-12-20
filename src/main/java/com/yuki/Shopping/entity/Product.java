package com.yuki.Shopping.entity;

import com.yuki.Shopping.constant.ProductStatus;
import com.yuki.Shopping.dto.ProductFormDto;
import com.yuki.Shopping.exception.OutOfStockException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String productName;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockCnt;

    @Column(nullable = false)
    private String productDetail;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    public void updateProduct(ProductFormDto productFormDto) {
        this.productName = productFormDto.getProductName();
        this.price = productFormDto.getPrice();
        this.stockCnt = productFormDto.getStockCnt();
        this.productDetail = productFormDto.getProductDetail();
        this.productStatus = productFormDto.getProductStatus();
    }

    public void removeStock(int stockCnt) {
        int restStock = this.stockCnt - stockCnt;
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockCnt + ")");
        }
        this.stockCnt = restStock;
    }
}
