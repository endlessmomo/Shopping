package com.yuki.Shopping.service;

import com.yuki.Shopping.entity.ProductImg;
import com.yuki.Shopping.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import java.util.Objects;

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

    public void updateProductImg(Long productImgId, MultipartFile productImgFile)
            throws Exception {
        if (!productImgFile.isEmpty()) {
            ProductImg savedProductImg = productImgRepository.findById(productImgId)
                    .orElseThrow(EntityExistsException::new);

            // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedProductImg.getImgName())) {
                fileService.deleteFile(productImgLocation + "/"
                        + savedProductImg.getImgName());
            }

            String oriImgName = productImgFile.getOriginalFilename();
            String imgName = fileService.uploadFiles(productImgLocation,
                    Objects.requireNonNull(oriImgName), productImgFile.getBytes());
            String imgUrl = "/images/product/" + imgName;
            savedProductImg.updateProductImg(oriImgName, imgName, imgUrl);
        }
    }
}
