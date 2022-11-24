package com.yuki.Shopping.controller;

import com.yuki.Shopping.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafExController {
    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model) {
        model.addAttribute("data", "타임리프 예제 입니다.");
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        ProductDto productDto =
                ProductDto.builder()
                        .productDetail("상품 상세 설명")
                        .productName("테스트 상품 1")
                        .price(10000)
                        .registered_date(LocalDateTime.now())
                        .build();

        model.addAttribute("productDto", productDto);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model) {
        List <ProductDto> productDtoList = new ArrayList <>();

        for (int i = 1; i < 10; i++) {
            ProductDto productDto =
                    ProductDto.builder()
                            .productDetail("상품 상세 설명")
                            .productName("테스트 상품 " + i)
                            .price(10000 * i)
                            .registered_date(LocalDateTime.now())
                            .build();

            productDtoList.add(productDto);
        }

        model.addAttribute("productDtoList", productDtoList);
        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping(value="/ex04")
    public String thymeleafExample04(Model model){
        List <ProductDto> productDtoList = new ArrayList <>();

        for(int i = 1; i <= 9; i++){
            ProductDto productDto =
                    ProductDto.builder()
                            .productDetail("상품 상세 설명" + i)
                            .productName("테스트 상품 " + i)
                            .price(10000 * i)
                            .registered_date(LocalDateTime.now())
                            .build();

            productDtoList.add(productDto);
        }

        model.addAttribute("productDtoList", productDtoList);
        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping(value="/ex05")
    public String thymeleafExample05(Model model){
        return "thymeleafEx/thymeleafEx05";
    }
    @GetMapping(value="/ex06")
    public String thymeleafExample06(Model model, String param1, String param2){
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thymeleafEx06";
    }

    @GetMapping(value="/ex07")
    public String thymeleafExample07(){
        return "thymeleafEx/thymeleafEx07";
    }
}
