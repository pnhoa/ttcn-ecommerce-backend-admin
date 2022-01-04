package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.CustomerDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ICustomerService extends UserDetailsService {

    List<CustomerDTO> findAll();

    CustomerDTO findById(Long theId);

    CustomerDTO findByUserName(String userName);

    CustomerDTO findByEmail(String email);

    MessageResponse createCustomer(CustomerDTO theCustomer);

    MessageResponse updateCustomer(Long theId, CustomerDTO theCustomer);

    void deleteCustomer(Long theId);

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String username);

    List<CustomerDTO> search(String key);

    Page<Customer> findAllPageAndSort(Pageable pagingSort);

    Page<Customer> findByUserNameContaining(String userName, Pageable pagingSort);

    Long count();
}
