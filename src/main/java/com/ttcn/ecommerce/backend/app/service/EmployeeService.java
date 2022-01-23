package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.EmployeeDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Employee;
import com.ttcn.ecommerce.backend.app.entity.Role;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.mapper.EmployeeMapper;
import com.ttcn.ecommerce.backend.app.repository.EmployeeRepository;
import com.ttcn.ecommerce.backend.app.repository.RoleRepository;
import com.ttcn.ecommerce.backend.app.utils.EmployeeDetailsImpl;
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
import java.util.*;

@Service
@Transactional
public class EmployeeService implements IEmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

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
    public List<EmployeeDTO> findAll() {
        List<Employee> theEmployees = employeeRepository.findAll();

        List<EmployeeDTO> theEmployeesDto = new ArrayList<>();

        for(Employee theEmployee :theEmployees) {
            if(theEmployee.getEnabled() == 1) {
                theEmployeesDto.add(EmployeeMapper.mapperToDTO(theEmployee));
            }
        }

        return theEmployeesDto;
    }

    @Override
    public EmployeeDTO findById(Long theId) {
        Optional<Employee> theEmployee = employeeRepository.findById(theId);

        if(!theEmployee.isPresent()) {
            throw new ResourceNotFoundException("Not found user with ID=" + theId);
        } else {
            if(theEmployee.get().getEnabled() == 1) {
                return EmployeeMapper.mapperToDTO(theEmployee.get());
            }
        }

        return null;
    }

    @Override
    public EmployeeDTO findByUserName(String userName) {
        Optional<Employee> theEmployee = employeeRepository.findByUserName(userName);

        if(!theEmployee.isPresent()) {
            throw new ResourceNotFoundException("Not found user with Username=" + userName);
        } else {
            if(theEmployee.get().getEnabled() == 1) {
                return EmployeeMapper.mapperToDTO(theEmployee.get());
            }
        }

        return null;
    }

    @Override
    public EmployeeDTO findByEmail(String email) {
        Optional<Employee> theEmployee = employeeRepository.findByEmail(email);

        if(!theEmployee.isPresent()) {
            throw new ResourceNotFoundException("Not found user with Username=" + email);
        } else {
            if(theEmployee.get().getEnabled() == 1) {
                return EmployeeMapper.mapperToDTO(theEmployee.get());
            }
        }

        return null;
    }

    @Override
    public MessageResponse createEmployee(EmployeeDTO theEmployeeDto) {

        Boolean existEmployee = employeeRepository.existsByUserName(theEmployeeDto.getUserName());

        if(existEmployee == true){
            return new MessageResponse("Exist username in database", HttpStatus.CONFLICT, LocalDateTime.now());
        } else {
            Employee theEmployee = new Employee();

            theEmployee.setCreatedDate(new Date());
            theEmployee.setUserName(theEmployeeDto.getUserName());
            theEmployee.setName(theEmployeeDto.getName());
            theEmployee.setPassword(passwordEncoder.encode(theEmployeeDto.getPassword()));
            theEmployee.setEmail(theEmployeeDto.getEmail());
            theEmployee.setPhoneNumber(theEmployeeDto.getPhoneNumber());
            theEmployee.setAddress(theEmployeeDto.getAddress());
            theEmployee.setGender(theEmployeeDto.getGender());
            theEmployee.setProfilePicture(theEmployeeDto.getProfilePicture());
            theEmployee.setEnabled(1);

            if(theEmployeeDto.getRoleCode() == null) {
                return new MessageResponse("Error create Employee!", HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
            }

            Set<Role> roles;
            if(theEmployeeDto.getRoleCode().equals("ROLE_ADMIN")) {
                roles = new HashSet<>(Arrays.asList(roleRepository.findByCode("ROLE_EMPLOYEE"),
                        roleRepository.findByCode("ROLE_ADMIN")));
            } else {
                roles = new HashSet<>(Arrays.asList(roleRepository.findByCode("ROLE_EMPLOYEE")));
            }
            theEmployee.setRoles(roles);


            employeeRepository.save(theEmployee);

            return new MessageResponse("Create employee successfully!", HttpStatus.ACCEPTED, LocalDateTime.now());
        }
    }

    @Override
    public MessageResponse updateEmployee(Long theId, EmployeeDTO theEmployeeDto) {
        Optional<Employee> theEmployee = employeeRepository.findById(theId);

        if(!theEmployee.isPresent()){
            throw new ResourceNotFoundException("Not found employee with ID=" + theId);
        } else {
            theEmployee.get().setModifiedDate(new Date());
            theEmployee.get().setModifiedBy(theEmployee.get().getUserName());
            theEmployee.get().setName(theEmployeeDto.getName());
            theEmployee.get().setEmail(theEmployeeDto.getEmail());
            theEmployee.get().setPhoneNumber(theEmployeeDto.getPhoneNumber());
            theEmployee.get().setAddress(theEmployeeDto.getAddress());
            theEmployee.get().setGender(theEmployeeDto.getGender());
            theEmployee.get().setProfilePicture(theEmployeeDto.getProfilePicture());

            Set<Role> roles;
            if(theEmployeeDto.getRoleCode().equals("ROLE_ADMIN")) {
                roles = new HashSet<>(Arrays.asList(roleRepository.findByCode("ROLE_EMPLOYEE"),
                        roleRepository.findByCode("ROLE_ADMIN")));
            } else {
                roles = new HashSet<>(Arrays.asList(roleRepository.findByCode("ROLE_EMPLOYEE")));
            }
            theEmployee.get().setRoles(roles);

            employeeRepository.save(theEmployee.get());
        }

        return new MessageResponse("Updated employee successfully!", HttpStatus.OK, LocalDateTime.now());
    }

    @Override
    public void deleteEmployee(Long theId) {
        Optional<Employee> theEmployee = employeeRepository.findById(theId);

        if(!theEmployee.isPresent()) {
            throw new ResourceNotFoundException("Not found Employee with ID=" + theId);
        } else {
            theEmployee.get().setEnabled(0);
            employeeRepository.save(theEmployee.get());
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUserName(String username) {
        return employeeRepository.existsByUserName(username);
    }

    @Override
    public List<EmployeeDTO> search(String key) {
        return null;
    }

    @Override
    public Page<Employee> findAllPageAndSort(Pageable pagingSort) {
        return employeeRepository.findAll(pagingSort);
    }

    @Override
    public Page<Employee> findByUserNameContaining(String userName, Pageable pagingSort) {
        return employeeRepository.findByUserNameContaining(userName, pagingSort);
    }

    @Override
    public Long count() {
        return employeeRepository.count();
    }

    @Override
    public Employee findByIdEmployee(Long employeeId) {
        Optional<Employee> theEmployee = employeeRepository.findById(employeeId);

        if(!theEmployee.isPresent()) {
            throw new ResourceNotFoundException("Not found user with ID=" + employeeId);
        } else {
            if(theEmployee.get().getEnabled() == 1) {
                return theEmployee.get();
            }
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByUserName(username);

        if ( (!employee.isPresent()) || employee.get().getEnabled() == 0) {
            throw new UsernameNotFoundException("Employee " + username + " was not found in the database");
        }

        return EmployeeDetailsImpl.build(employee.get());
    }
}
