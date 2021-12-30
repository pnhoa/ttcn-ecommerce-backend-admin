package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.dto.ProductDTO;
import com.ttcn.ecommerce.backend.app.entity.Product;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long theId) throws ResourceNotFoundException {
        return productRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Not found product with ID=" + theId));
    }

    @Override
    public MessageResponse createProduct(ProductDTO theProductDto) {
        return null;
    }

    @Override
    public MessageResponse updateProduct(Long theId, ProductDTO theProductDto) {
        return null;
    }

    @Override
    public void deleteProduct(Long theId) {

    }
}
