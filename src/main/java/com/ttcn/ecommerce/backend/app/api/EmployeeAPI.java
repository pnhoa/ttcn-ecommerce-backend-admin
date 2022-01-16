package com.ttcn.ecommerce.backend.app.api;

import com.ttcn.ecommerce.backend.app.dto.EmployeeDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Employee;
import com.ttcn.ecommerce.backend.app.service.IEmployeeService;
import com.ttcn.ecommerce.backend.app.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeAPI {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<List<Employee>> findAll(@RequestParam(name = "q", required = false) String userName,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int limit,
                                                  @RequestParam(defaultValue = "id,ASC") String[] sort){

        try {

            Pageable pagingSort = CommonUtils.sortItem(page, limit, sort);
            Page<Employee> employeePage;

            if(userName == null) {
                employeePage = employeeService.findAllPageAndSort(pagingSort);
            } else {
                employeePage = employeeService.findByUserNameContaining(userName, pagingSort);
            }

            return new ResponseEntity<>(employeePage.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable("id") Long theId){

        EmployeeDTO theEmployee = employeeService.findById(theId);
        return new ResponseEntity<>(theEmployee, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createEmployee(@Valid @RequestBody EmployeeDTO theEmployeeDto, BindingResult theBindingResult){

        if(theBindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for create employee", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = employeeService.createEmployee(theEmployeeDto);
        return new ResponseEntity<MessageResponse>(messageResponse, messageResponse.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateEmployee(@PathVariable("id") Long theId,
                                                          @Valid @RequestBody EmployeeDTO theEmployeeDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for update employee", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = employeeService.updateEmployee(theId, theEmployeeDto);
        return new ResponseEntity<MessageResponse>(messageResponse, messageResponse.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long theId){

        employeeService.deleteEmployee(theId);
        return new ResponseEntity<>(new MessageResponse("Delete successfully!", HttpStatus.OK, LocalDateTime.now()), HttpStatus.OK);
    }


    @GetMapping("/count")
    public ResponseEntity<?> count(){
        return new ResponseEntity<>(employeeService.count(), HttpStatus.OK);
    }
}
