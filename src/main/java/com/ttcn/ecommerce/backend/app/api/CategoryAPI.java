package com.ttcn.ecommerce.backend.app.api;

import com.ttcn.ecommerce.backend.app.entity.Category;
import com.ttcn.ecommerce.backend.app.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAll(){

        List<Category> categories = categoryService.findAll();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") Long theId){
        Category theCategory = categoryService.findById(theId);

        return new ResponseEntity<>(theCategory, HttpStatus.OK);
    }

}
