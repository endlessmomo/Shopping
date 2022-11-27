package com.yuki.Shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    @GetMapping(value="/admin/product/new")
    public String productForm(){
        return "/product/productForm";
    }
}
