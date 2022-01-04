package com.ttcn.ecommerce.backend.app.mapper;

import com.ttcn.ecommerce.backend.app.dto.CustomerDTO;
import com.ttcn.ecommerce.backend.app.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO mapperToDTO(Customer theCustomer) {

        CustomerDTO theCustomerDto = new CustomerDTO();

        theCustomerDto.setId(theCustomer.getId());
        theCustomerDto.setUserName(theCustomer.getUserName());
        theCustomerDto.setName(theCustomer.getName());
        theCustomerDto.setEmail(theCustomer.getEmail());
        theCustomerDto.setPhoneNumber(theCustomer.getPhoneNumber());
        theCustomerDto.setAddress(theCustomer.getAddress());
        theCustomerDto.setGender(theCustomer.getGender());
        theCustomerDto.setProfilePicture(theCustomer.getProfilePicture());
        theCustomerDto.setEnabled(theCustomer.getEnabled());
        theCustomerDto.setRoleCode("ROLE_CUSTOMER");

        return theCustomerDto;
    }

    public static Customer mapperToEntity(CustomerDTO theCustomerDto){
        Customer theCustomer = new Customer();

        theCustomer.setUserName(theCustomerDto.getUserName());
        theCustomer.setName(theCustomerDto.getName());
        theCustomer.setEmail(theCustomerDto.getEmail());
        theCustomer.setPhoneNumber(theCustomerDto.getPhoneNumber());
        theCustomer.setAddress(theCustomerDto.getAddress());
        theCustomer.setGender(theCustomerDto.getGender());
        theCustomer.setProfilePicture(theCustomerDto.getProfilePicture());
        theCustomer.setEnabled(1);

        return theCustomer;
    }

}
