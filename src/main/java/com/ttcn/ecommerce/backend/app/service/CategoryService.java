package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.CategoryDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Category;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public MessageResponse createCategory(CategoryDTO theCategoryDto) {

        Category theCategory = new Category();

        theCategory.setCreatedDate(new Date());
        theCategory.setCreatedBy("");
        theCategory.setName(theCategoryDto.getName());
        theCategory.setDescription(theCategoryDto.getDescription());
        theCategory.setThumbnail(theCategoryDto.getThumbnail());

        categoryRepository.save(theCategory);

        return new MessageResponse("Create category successfully!", HttpStatus.CREATED, LocalDateTime.now());
    }

    @Override
    public MessageResponse updateCategory(Long theId, CategoryDTO theCategoryDto) {
        Optional<Category> theCategory = categoryRepository.findById(theId);

        if(!theCategory.isPresent()) {
            throw new ResourceNotFoundException("Not found category with ID=" + theId);
        } else {

            theCategory.get().setModifiedDate(new Date());
            theCategory.get().setModifiedBy("");
            theCategory.get().setName(theCategoryDto.getName());
            theCategory.get().setDescription(theCategoryDto.getDescription());
            theCategory.get().setThumbnail(theCategoryDto.getThumbnail());

            categoryRepository.save(theCategory.get());
        }

        return new MessageResponse("Updated category successfully!", HttpStatus.OK, LocalDateTime.now());
    }

    @Override
    public void deleteCategory(Long theId) {
        Category theCategory = categoryRepository.findById(theId).orElseThrow(
                () -> new ResourceNotFoundException("Not found category with ID=" + theId));

        categoryRepository.delete(theCategory);
    }

    @Override
    public Page<Category> findAllPageAndSort(Pageable pagingSort) {
        return categoryRepository.findAll(pagingSort);
    }

    @Override
    public Page<Category> findByNameContaining(String categoryName, Pageable pagingSort) {
        return categoryRepository.findByNameContaining(categoryName, pagingSort);
    }
}
