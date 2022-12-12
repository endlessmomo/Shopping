package com.yuki.Shopping.repository;

import com.yuki.Shopping.dto.MainProductDto;
import com.yuki.Shopping.dto.ProductSearchDto;
import com.yuki.Shopping.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page <Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable);

    Page <MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable);
}
