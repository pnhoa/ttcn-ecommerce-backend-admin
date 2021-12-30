package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.entity.Category;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long theId) throws ResourceNotFoundException {
        return categoryRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Not found category with ID= " + theId));
    }
}
