package com.ttcn.ecommerce.backend.app.api;

import com.ttcn.ecommerce.backend.app.dto.CustomerDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Customer;
import com.ttcn.ecommerce.backend.app.service.ICustomerService;
import com.ttcn.ecommerce.backend.app.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Customer>> findAll(@RequestParam(name = "q", required = false) String userName,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int limit,
                                                  @RequestParam(defaultValue = "id,ASC") String[] sort){

        try {

            Pageable pagingSort = CommonUtils.sortItem(page, limit, sort);
            Page<Customer> customerPage;

            if(userName == null) {
                customerPage = customerService.findAllPageAndSort(pagingSort);
            } else {
                customerPage = customerService.findByUserNameContaining(userName, pagingSort);
            }

            return new ResponseEntity<>(customerPage.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<?> findById(@PathVariable("id") Long theId){

        CustomerDTO theCustomer = customerService.findById(theId);
        if(theCustomer == null) {
            return new ResponseEntity<>(new MessageResponse("This account was banned.", HttpStatus.LOCKED, LocalDateTime.now()), HttpStatus.LOCKED);
        }
        return new ResponseEntity<>(theCustomer, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createCustomer(@Valid @RequestBody CustomerDTO theCustomerDto, BindingResult theBindingResult){

        if(theBindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for create customer", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = customerService.createCustomer(theCustomerDto);
        return new ResponseEntity<MessageResponse>(messageResponse, messageResponse.getStatus());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<MessageResponse> updateCustomer(@PathVariable("id") Long theId,
                                                          @Valid @RequestBody CustomerDTO theCustomerDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for update customer", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = customerService.updateCustomer(theId, theCustomerDto);
        return new ResponseEntity<MessageResponse>(messageResponse, messageResponse.getStatus());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long theId){

        customerService.deleteCustomer(theId);
        return new ResponseEntity<>(new MessageResponse("Delete successfully!", HttpStatus.OK, LocalDateTime.now()), HttpStatus.OK);
    }


    @GetMapping("/count")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<?> count(){
        return new ResponseEntity<>(customerService.count(), HttpStatus.OK);
    }
}
