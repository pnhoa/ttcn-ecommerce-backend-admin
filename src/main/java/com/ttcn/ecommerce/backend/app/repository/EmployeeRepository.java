package com.ttcn.ecommerce.backend.app.repository;

import com.ttcn.ecommerce.backend.app.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUserName(String username);

    Optional<Employee> findByEmail(String email);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    @Query("SELECT c FROM Employee c WHERE lower(c.userName) LIKE %?1%"
            + " OR lower(c.name) LIKE %?1% OR lower(c.email) LIKE %?1% OR lower(c.phoneNumber) LIKE %?1%" )
    List<Employee> search(String key);

    Page<Employee> findByUserNameContaining(String userName, Pageable pageable);
}
