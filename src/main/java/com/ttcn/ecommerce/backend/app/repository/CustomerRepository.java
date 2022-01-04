package com.ttcn.ecommerce.backend.app.repository;

import com.ttcn.ecommerce.backend.app.entity.Customer;
import com.ttcn.ecommerce.backend.app.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserName(String username);

    Optional<Customer> findByEmail(String email);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE lower(c.userName) LIKE %?1%"
            + " OR lower(c.name) LIKE %?1% OR lower(c.email) LIKE %?1% OR lower(c.phoneNumber) LIKE %?1%" )
    List<Customer> search(String key);

    Page<Customer> findByUserNameContaining(String userName, Pageable pageable);
}
