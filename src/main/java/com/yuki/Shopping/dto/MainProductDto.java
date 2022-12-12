package com.yuki.Shopping.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainProductDto {
    private Long id;

    private String productName;

    private String productDetail;

    private String imgUrl;

    private Integer price;

    @QueryProjection
    public MainProductDto(Long id, String productName, String productDetail
            , String imgUrl, Integer price) {
        this.id = id;
        this.productName = productName;
        this.productDetail = productDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
