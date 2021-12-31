package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.dto.ProductDTO;
import com.ttcn.ecommerce.backend.app.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findAll();

    Product findById(Long theId);

    MessageResponse createProduct(ProductDTO theProductDto);

    MessageResponse updateProduct(Long theId, ProductDTO theProductDto);

    void deleteProduct(Long theId);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> search(String key);

}
