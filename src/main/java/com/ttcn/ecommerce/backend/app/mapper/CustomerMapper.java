package com.ttcn.ecommerce.backend.app.mapper;

import com.ttcn.ecommerce.backend.app.dto.CustomerDTO;
import com.ttcn.ecommerce.backend.app.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO mapper(Customer theCustomer) {

        CustomerDTO theCustomerDto = new CustomerDTO();

        theCustomerDto.setId(theCustomer.getId());
        theCustomerDto.setUserName(theCustomer.getUserName());
        theCustomerDto.setName(theCustomer.getName());
        theCustomerDto.setEmail(theCustomer.getEmail());
        theCustomerDto.setPhoneNumber(theCustomer.getPhoneNumber());
        theCustomerDto.setAddress(theCustomer.getAddress());
        theCustomerDto.setGender(theCustomer.getGender());
        theCustomerDto.setProfilePicture(theCustomer.getProfilePicture());
        theCustomerDto.setRoleCode("ROLE_CUSTOMER");

        return theCustomerDto;
    }

}
