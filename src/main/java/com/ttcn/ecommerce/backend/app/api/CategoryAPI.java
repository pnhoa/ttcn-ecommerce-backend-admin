package com.ttcn.ecommerce.backend.app.api;

import com.ttcn.ecommerce.backend.app.dto.CategoryDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Category;
import com.ttcn.ecommerce.backend.app.service.ICategoryService;
import com.ttcn.ecommerce.backend.app.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAll(@RequestParam(name = "categoryName_contains", required = false) String categoryName,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int limit,
                                                 @RequestParam(defaultValue = "id,ASC") String[] sort){

        try {

            Pageable pagingSort = CommonUtils.sortItem(page, limit, sort);
            Page<Category> categoryPage;

            if(categoryName == null) {
                categoryPage = categoryService.findAllPageAndSort(pagingSort);
            } else {
                categoryPage = categoryService.findByNameContaining(categoryName, pagingSort);
            }

            if(categoryPage.getContent().isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categoryPage.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") Long theId){
        Category theCategory = categoryService.findById(theId);

        return new ResponseEntity<>(theCategory, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createCategory(@Valid @RequestBody CategoryDTO theCategoryDto, BindingResult theBindingResult){

        if(theBindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for create category", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = categoryService.createCategory(theCategoryDto);
        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCategory(@PathVariable("id") Long theId,
                                                         @Valid @RequestBody CategoryDTO theCategoryDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for update category", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = categoryService.updateCategory(theId, theCategoryDto);
        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long theId){

        categoryService.deleteCategory(theId);
        return new ResponseEntity<>(new MessageResponse("Deleted successfully!", HttpStatus.OK, LocalDateTime.now()), HttpStatus.OK);
    }

}
