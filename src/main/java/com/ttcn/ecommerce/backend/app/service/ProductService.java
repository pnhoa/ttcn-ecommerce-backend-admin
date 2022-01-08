package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.dto.ProductDTO;
import com.ttcn.ecommerce.backend.app.entity.Category;
import com.ttcn.ecommerce.backend.app.entity.Product;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.repository.ProductRepository;
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
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAllPageAndSort(Pageable pagingSort) {
        return  productRepository.findAll(pagingSort);
    }

    @Override
    public Product findById(Long theId) throws ResourceNotFoundException {
        return productRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Not found product with ID=" + theId));
    }

    @Override
    public MessageResponse createProduct(ProductDTO theProductDto) {

        Product theProduct = new Product();

        theProduct.setName(theProductDto.getName());
        theProduct.setBrand(theProductDto.getBrand());
        theProduct.setShortDescription(theProductDto.getShortDescription());
        theProduct.setDescription(theProductDto.getDescription());
        theProduct.setPrice(theProductDto.getPrice());
        theProduct.setThumbnail(theProductDto.getThumbnail());
        theProduct.setUnitInStock(theProductDto.getUnitInStock());
        theProduct.setCategory(categoryService.findById(theProductDto.getCategoryId()));
        theProduct.setCreatedDate(new Date());
        theProduct.setCreatedBy(theProductDto.getCreatedBy());

        productRepository.save(theProduct);

        return new MessageResponse("Create product successfully!", HttpStatus.CREATED, LocalDateTime.now());
    }

    @Override
    public MessageResponse updateProduct(Long theId, ProductDTO theProductDto) throws ResourceNotFoundException {

        Optional<Product> theProduct = productRepository.findById(theId);

        if(!theProduct.isPresent()) {
            throw new ResourceNotFoundException("Not found product with ID=" + theId);
        } else {
            theProduct.get().setName(theProductDto.getName());
            theProduct.get().setBrand(theProductDto.getBrand());
            theProduct.get().setShortDescription(theProductDto.getShortDescription());
            theProduct.get().setDescription(theProductDto.getDescription());
            theProduct.get().setPrice(theProductDto.getPrice());
            theProduct.get().setThumbnail(theProductDto.getThumbnail());
            theProduct.get().setUnitInStock(theProductDto.getUnitInStock());
            theProduct.get().setCategory(categoryService.findById(theProductDto.getCategoryId()));
            theProduct.get().setModifiedDate(new Date());
            theProduct.get().setModifiedBy(theProductDto.getModifiedBy());

            productRepository.save(theProduct.get());
        }

        return new MessageResponse("Update product successfully!" , HttpStatus.OK, LocalDateTime.now());
    }

    @Override
    public void deleteProduct(Long theId) throws ResourceNotFoundException {

        Product theProduct = productRepository.findById(theId).orElseThrow(
                () -> new ResourceNotFoundException("Not found product with ID=" + theId));

        productRepository.delete(theProduct);

    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findProductsByCategoryId(categoryId);
    }

    @Override
    public List<Product> search(String key) {
        return productRepository.search(key.toLowerCase());
    }

    @Override
    public Page<Product> findByNameContaining(String productName, Pageable pagingSort) {
        return productRepository.findByNameContaining(productName, pagingSort);
    }

    @Override
    public Long count() {
        return productRepository.count();
    }

    @Override
    public Page<Product> findByCategoryIdPageAndSort(Long categoryId, Pageable pagingSort) {

        Category category = categoryService.findById(categoryId);

        if(category == null){
            throw  new ResourceNotFoundException("Not found category with ID= " + categoryId);
        } else {
            return productRepository.findByCategoryId(categoryId, pagingSort);
        }
    }
}
