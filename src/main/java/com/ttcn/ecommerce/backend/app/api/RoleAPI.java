package com.ttcn.ecommerce.backend.app.api;

import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.dto.RoleDTO;
import com.ttcn.ecommerce.backend.app.entity.Role;
import com.ttcn.ecommerce.backend.app.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class RoleAPI {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable("id") Long theId){

        Role theRole = roleService.findById(theId);
        return new ResponseEntity<>(theRole, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createRole(@Valid @RequestBody RoleDTO theRoleDto, BindingResult theBindingResult){

        if(theBindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for create role", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = roleService.createRole(theRoleDto);
        return new ResponseEntity<MessageResponse>(messageResponse, messageResponse.getStatus());
    }


    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateRole(@PathVariable("id") Long theId,
                                                          @Valid @RequestBody RoleDTO theRoleDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for update Role", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = roleService.updateRole(theId, theRoleDto);
        return new ResponseEntity<MessageResponse>(messageResponse, messageResponse.getStatus());
    }

}
