package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.EmployeeDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IEmployeeService extends UserDetailsService {

    List<EmployeeDTO> findAll();

    EmployeeDTO findById(Long theId);

    EmployeeDTO findByUserName(String userName);

    EmployeeDTO findByEmail(String email);

    MessageResponse createEmployee(EmployeeDTO theEmployee);

    MessageResponse updateEmployee(Long theId, EmployeeDTO theEmployee);

    void deleteEmployee(Long theId);

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String username);

    List<EmployeeDTO> search(String key);

    Page<Employee> findAllPageAndSort(Pageable pagingSort);

    Page<Employee> findByUserNameContaining(String userName, Pageable pagingSort);

    Long count();

    Employee findByIdEmployee(Long employeeId);
}
