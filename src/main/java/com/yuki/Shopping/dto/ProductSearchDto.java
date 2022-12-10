package com.yuki.Shopping.dto;

import com.yuki.Shopping.constant.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchDto {
    private String searchDateType;

    private ProductStatus searchSellStatus;

    private String searchBy;

    private String searchQuery;
}
