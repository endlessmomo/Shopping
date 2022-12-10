package com.yuki.Shopping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductDto {
    private Long id;
    private String productName;
    private Integer price;
    private String productDetail;
    private Long stockCnt;
    private String sellStatus;
    private LocalDateTime registered_date;
    private LocalDateTime updated_date;
}
