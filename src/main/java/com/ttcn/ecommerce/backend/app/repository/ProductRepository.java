package com.ttcn.ecommerce.backend.app.repository;

import com.ttcn.ecommerce.backend.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
