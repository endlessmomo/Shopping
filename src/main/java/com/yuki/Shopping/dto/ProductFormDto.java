package com.yuki.Shopping.dto;

import com.yuki.Shopping.constant.ProductStatus;
import com.yuki.Shopping.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductFormDto {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String productName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String productDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockCnt;

    private ProductStatus productStatus;

    private List <ProductImgDto> productImgDtoList = new ArrayList <>();

    private List <Long> productImgIds = new ArrayList <>();
    private static ModelMapper modelMapper = new ModelMapper();

    public Product createProduct() {
        return modelMapper.map(this, Product.class);
    }

    public static ProductFormDto of(Product product) {
        return modelMapper.map(product, ProductFormDto.class);
    }
}
