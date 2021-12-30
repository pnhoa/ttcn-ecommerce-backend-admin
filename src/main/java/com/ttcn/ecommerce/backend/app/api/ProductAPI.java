package com.ttcn.ecommerce.backend.app.api;

import com.ttcn.ecommerce.backend.app.entity.Product;
import com.ttcn.ecommerce.backend.app.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public ResponseEntity<List<Product>> findAll(){

        List<Product> theProducts = productService.findAll();

        return new ResponseEntity<>(theProducts, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long theId){

        Product theProduct = productService.findById(theId);

        return new ResponseEntity<>(theProduct, HttpStatus.OK);
    }
}
