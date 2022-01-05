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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Qualifier("passwordEncoder")
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
                theCustomersDto.add(CustomerMapper.mapperToDTO(theCustomer));
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
                return CustomerMapper.mapperToDTO(theCustomer.get());
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
                return CustomerMapper.mapperToDTO(theCustomer.get());
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
                return CustomerMapper.mapperToDTO(theCustomer.get());
            }
        }

        return null;
    }

    @Override
    public MessageResponse createCustomer(CustomerDTO theCustomerDto) {

        Boolean existCustomer = customerRepository.existsByUserName(theCustomerDto.getUserName());

        if(existCustomer == true){
            return new MessageResponse("Exist username in database", HttpStatus.CONFLICT, LocalDateTime.now());
        } else {
            Customer theCustomer = new Customer();

            theCustomer.setCreatedDate(new Date());
            theCustomer.setUserName(theCustomerDto.getUserName());
            theCustomer.setName(theCustomerDto.getName());
            theCustomer.setPassword(passwordEncoder.encode(theCustomerDto.getPassword()));
            theCustomer.setEmail(theCustomerDto.getEmail());
            theCustomer.setPhoneNumber(theCustomerDto.getPhoneNumber());
            theCustomer.setAddress(theCustomerDto.getAddress());
            theCustomer.setGender(theCustomerDto.getGender());
            theCustomer.setProfilePicture(theCustomerDto.getProfilePicture());
            theCustomer.setEnabled(1);
            theCustomer.setRole(roleRepository.findByCode("ROLE_CUSTOMER"));

            customerRepository.save(theCustomer);

            return new MessageResponse("Create customer successfully!", HttpStatus.ACCEPTED, LocalDateTime.now());
        }


    }

    @Override
    public MessageResponse updateCustomer(Long theId, CustomerDTO theCustomerDto) {

        Optional<Customer> theCustomer = customerRepository.findById(theId);

        if(!theCustomer.isPresent()){
            throw new ResourceNotFoundException("Not found customer with ID=" + theId);
        } else {
            theCustomer.get().setModifiedDate(new Date());
            theCustomer.get().setModifiedBy(theCustomer.get().getUserName());
            theCustomer.get().setName(theCustomerDto.getName());
            theCustomer.get().setEmail(theCustomerDto.getEmail());
            theCustomer.get().setPhoneNumber(theCustomerDto.getPhoneNumber());
            theCustomer.get().setAddress(theCustomerDto.getAddress());
            theCustomer.get().setGender(theCustomerDto.getGender());
            theCustomer.get().setProfilePicture(theCustomerDto.getProfilePicture());

            customerRepository.save(theCustomer.get());
        }

        return new MessageResponse("Updated customer successfully!", HttpStatus.OK, LocalDateTime.now());
    }

    @Override
    public void deleteCustomer(Long theId) {
        Optional<Customer> theCustomer = customerRepository.findById(theId);

        if(!theCustomer.isPresent()){
            throw new ResourceNotFoundException("Not found customer with ID=" + theId);
        } else {
            theCustomer.get().setEnabled(0);
            customerRepository.save(theCustomer.get());
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUserName(String username) {
        return customerRepository.existsByUserName(username);
    }

    @Override
    public List<CustomerDTO> search(String key) {
        return null;
    }

    @Override
    public Page<Customer> findAllPageAndSort(Pageable pagingSort) {
        return customerRepository.findAll(pagingSort);
    }

    @Override
    public Page<Customer> findByUserNameContaining(String userName, Pageable pagingSort) {
        return customerRepository.findByUserNameContaining(userName, pagingSort);
    }

    @Override
    public Long count() {
        return customerRepository.count();
    }

}
