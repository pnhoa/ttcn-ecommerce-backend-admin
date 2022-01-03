package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.CustomerDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ICustomerService extends UserDetailsService {

    List<CustomerDTO> findAll();

    CustomerDTO findById(Long theId);

    CustomerDTO findByUserName(String userName);

    CustomerDTO findByEmail(String email);

    MessageResponse createUser(CustomerDTO theCustomer);

    MessageResponse updateUser(Long theId, CustomerDTO theCustomer);

    void deleteCustomer(Long theId);

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String username);

    List<CustomerDTO> search(String key);
}
