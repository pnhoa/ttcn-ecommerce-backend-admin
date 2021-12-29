package com.ttcn.ecommerce.backend.app.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @GetMapping("")
    public String test() {
        return "Hello";
    }
}
