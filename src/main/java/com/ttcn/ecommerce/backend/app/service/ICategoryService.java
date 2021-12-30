package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.entity.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findAll();

    Category findById(Long theId);
}
