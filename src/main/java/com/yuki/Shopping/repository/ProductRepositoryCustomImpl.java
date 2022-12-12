package com.yuki.Shopping.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuki.Shopping.constant.ProductStatus;
import com.yuki.Shopping.dto.MainProductDto;
import com.yuki.Shopping.dto.ProductSearchDto;
import com.yuki.Shopping.dto.QMainProductDto;
import com.yuki.Shopping.entity.Product;
import com.yuki.Shopping.entity.QProduct;
import com.yuki.Shopping.entity.QProductImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ProductStatus searchSellStatus) {
        return searchSellStatus == null ? null : QProduct.product.productStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QProduct.product.registeredTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("productName", searchBy)) {
            return QProduct.product.productName.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createBy", searchBy)) {
            return QProduct.product.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page <Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
        List <Product> results = queryFactory
                .selectFrom(QProduct.product)
                .where(regDtsAfter(productSearchDto.getSearchDateType())
                        , searchSellStatusEq(productSearchDto.getSearchSellStatus())
                        , searchByLike(productSearchDto.getSearchBy(), productSearchDto.getSearchQuery()))
                .orderBy(QProduct.product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl <>(results, pageable, results.size());
    }

    private BooleanExpression productNameLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null
                : QProduct.product.productName.like("%" + searchQuery + "%");
    }

    @Override
    public Page <MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductImg productImg = QProductImg.productImg;

        List <MainProductDto> content = queryFactory.
                select(
                        new QMainProductDto(
                                product.id,
                                product.productName,
                                product.productDetail,
                                productImg.imgUrl,
                                product.price
                        )
                )
                .from(productImg)
                .join(productImg.product, product)
                .where(productImg.repImgYn.eq("Y"))
                .where(productNameLike(productSearchDto.getSearchQuery()))
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl <>(content, pageable, content.size());
    }
}
