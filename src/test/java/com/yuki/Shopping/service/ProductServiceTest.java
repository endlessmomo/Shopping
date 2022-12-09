package com.yuki.Shopping.service;

import com.yuki.Shopping.constant.ProductStatus;
import com.yuki.Shopping.dto.ProductFormDto;
import com.yuki.Shopping.entity.Product;
import com.yuki.Shopping.entity.ProductImg;
import com.yuki.Shopping.repository.ProductImgRepository;
import com.yuki.Shopping.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImgRepository productImgRepository;

    List <MultipartFile> createMultipartFiles() throws Exception{
        List<MultipartFile> multipartFileList = new ArrayList <>();

        for(int i = 0; i < 5; i++){
            String path = "/Users/yuki/Projects/shop/product";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName
                            , "image/jpg", new byte[]{1,2,3,4,5});
            multipartFileList.add(multipartFile);
        }
       return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveProduct() throws Exception{
        ProductFormDto productFormDto = new ProductFormDto();
        productFormDto.setProductName("테스트 상품");
        productFormDto.setProductStatus(ProductStatus.SELL);
        productFormDto.setProductDetail("테스트 상품 입니다.");
        productFormDto.setPrice(1000);
        productFormDto.setStockCnt(100);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long productId = productService.saveProduct(productFormDto, multipartFileList);

        List<ProductImg> productImgList
                = productImgRepository.findByProductIdOrderByIdAsc(productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(productFormDto.getProductName(), product.getProductName());

    }
}