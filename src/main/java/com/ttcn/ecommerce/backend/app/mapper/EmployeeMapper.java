package com.ttcn.ecommerce.backend.app.mapper;

import com.ttcn.ecommerce.backend.app.dto.EmployeeDTO;
import com.ttcn.ecommerce.backend.app.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDTO mapperToDTO(Employee theEmployee) {

        EmployeeDTO theEmployeeDto = new EmployeeDTO();

        theEmployeeDto.setId(theEmployee.getId());
        theEmployeeDto.setUserName(theEmployee.getUserName());
        theEmployeeDto.setName(theEmployee.getName());
        theEmployeeDto.setEmail(theEmployee.getEmail());
        theEmployeeDto.setPhoneNumber(theEmployee.getPhoneNumber());
        theEmployeeDto.setAddress(theEmployee.getAddress());
        theEmployeeDto.setGender(theEmployee.getGender());
        theEmployeeDto.setProfilePicture(theEmployee.getProfilePicture());
        theEmployeeDto.setEnabled(theEmployee.getEnabled());

        if(theEmployee.getRoles().size() == 2) {
            theEmployeeDto.setRoleCode("ROLE_ADMIN");
        } else {
            theEmployeeDto.setRoleCode("ROLE_EMPLOYEE");
        }

        return theEmployeeDto;
    }

}
