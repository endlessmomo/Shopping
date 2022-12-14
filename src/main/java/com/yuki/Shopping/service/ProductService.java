package com.yuki.Shopping.service;

import com.yuki.Shopping.dto.MainProductDto;
import com.yuki.Shopping.dto.ProductFormDto;
import com.yuki.Shopping.dto.ProductImgDto;
import com.yuki.Shopping.dto.ProductSearchDto;
import com.yuki.Shopping.entity.Product;
import com.yuki.Shopping.entity.ProductImg;
import com.yuki.Shopping.repository.ProductImgRepository;
import com.yuki.Shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;
    private final ProductImgService productImgService;

    public Long saveProduct(ProductFormDto productFormDto
            , List <MultipartFile> productImgFileList) throws Exception {

        // 상품 등록
        Product product = productFormDto.createProduct();
        productRepository.save(product);

        //이미지 등록
        for (int i = 0; i < productImgFileList.size(); i++) {
            ProductImg productImg = new ProductImg();
            productImg.setProduct(product);
            if (i == 0) {
                productImg.setRepImgYn("Y");
            } else
                productImg.setRepImgYn("N");
            productImgService.saveProductImg(productImg, productImgFileList.get(i));
        }

        return product.getId();
    }


    @Transactional(readOnly = true)
    public ProductFormDto getProductDtl(Long productId) {

        List <ProductImg> productImgList =
                productImgRepository.findByProductIdOrderByIdAsc(productId);
        List <ProductImgDto> productImgDtoList = new ArrayList <>();

        for (ProductImg productImg : productImgList) {
            ProductImgDto productImgDto = ProductImgDto.of(productImg);
            productImgDtoList.add(productImgDto);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        ProductFormDto productFormDto = ProductFormDto.of(product);
        productFormDto.setProductImgDtoList(productImgDtoList);
        return productFormDto;
    }

    public Long updateProduct(ProductFormDto productFormDto,
            List <MultipartFile> productImgFileList) throws Exception {
        // 상품 수정
        Product product = productRepository.findById(productFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        product.updateProduct(productFormDto);

        List <Long> productImgIds = productFormDto.getProductImgIds();

        // 이미지 등록
        for (int i = 0; i < productImgFileList.size(); i++) {
            productImgService.updateProductImg(productImgIds.get(i),
                    productImgFileList.get(i));
        }

        System.out.println(product.getId());
        return product.getId();
    }

    @Transactional(readOnly = true)
    public Page <Product> getAdminProductPage(ProductSearchDto productSearchDto
            , Pageable pageable) {
        return productRepository.getAdminProductPage(productSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page <MainProductDto> getMainProductPage(ProductSearchDto productSearchDto,
            Pageable pageable) {
        return productRepository.getMainProductPage(productSearchDto, pageable);
    }
}
