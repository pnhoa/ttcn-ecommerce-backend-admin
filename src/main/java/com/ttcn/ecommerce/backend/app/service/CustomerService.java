package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.CustomerDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Customer;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.mapper.CustomerMapper;
import com.ttcn.ecommerce.backend.app.repository.CustomerRepository;
import com.ttcn.ecommerce.backend.app.repository.RoleRepository;
import com.ttcn.ecommerce.backend.app.utils.CustomerDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUserName(username);

        if ( (!customer.isPresent()) || customer.get().getEnabled() == 0) {
            throw new UsernameNotFoundException("Customer " + username + " was not found in the database");
        }

        return CustomerDetailsImpl.build(customer.get());
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> theCustomers = customerRepository.findAll();

        List<CustomerDTO> theCustomersDto = new ArrayList<>();

        for(Customer theCustomer :theCustomers) {
            if(theCustomer.getEnabled() == 1) {
                theCustomersDto.add(CustomerMapper.mapper(theCustomer));
            }
        }

        return theCustomersDto;
    }

    @Override
    public CustomerDTO findById(Long theId) {
        Optional<Customer> theCustomer = customerRepository.findById(theId);

        if(!theCustomer.isPresent()) {
            throw new ResourceNotFoundException("Not found user with ID=" + theId);
        } else {
            if(theCustomer.get().getEnabled() == 1) {
                return CustomerMapper.mapper(theCustomer.get());
            }
        }

        return null;
    }

    @Override
    public CustomerDTO findByUserName(String userName) {
        Optional<Customer> theCustomer = customerRepository.findByUserName(userName);

        if(!theCustomer.isPresent()) {
            throw new ResourceNotFoundException("Not found user with Username=" + userName);
        } else {
            if(theCustomer.get().getEnabled() == 1) {
                return CustomerMapper.mapper(theCustomer.get());
            }
        }

        return null;
    }

    @Override
    public CustomerDTO findByEmail(String email) {
        Optional<Customer> theCustomer = customerRepository.findByEmail(email);

        if(!theCustomer.isPresent()) {
            throw new ResourceNotFoundException("Not found user with Username=" + email);
        } else {
            if(theCustomer.get().getEnabled() == 1) {
                return CustomerMapper.mapper(theCustomer.get());
            }
        }

        return null;
    }

    @Override
    public MessageResponse createUser(CustomerDTO theCustomer) {
        return null;
    }

    @Override
    public MessageResponse updateUser(Long theId, CustomerDTO theCustomer) {
        return null;
    }

    @Override
    public void deleteCustomer(Long theId) {

    }

    @Override
    public Boolean existsByEmail(String email) {
        return null;
    }

    @Override
    public Boolean existsByUserName(String username) {
        return null;
    }

    @Override
    public List<CustomerDTO> search(String key) {
        return null;
    }

}
