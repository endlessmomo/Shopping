package com.yuki.Shopping.service;

import com.yuki.Shopping.entity.ProductImg;
import com.yuki.Shopping.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImgService {

    @Value("${productImgLocation}")
    private String productImgLocation;

    private final ProductImgRepository productImgRepository;

    private final FileService fileService;

    public void saveProductImg(ProductImg productImg, MultipartFile productImgFile)
            throws Exception {
        String oriImgName = productImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFiles(productImgLocation, oriImgName
                    , productImgFile.getBytes());
            imgUrl = "/images/product/" + imgName;
        }

        productImg.updateProductImg(oriImgName, imgName, imgUrl);
        productImgRepository.save(productImg);
    }
}
