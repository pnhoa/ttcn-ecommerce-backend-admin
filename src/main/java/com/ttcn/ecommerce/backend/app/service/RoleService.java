package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.entity.Role;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService implements IRoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long theId) throws ResourceNotFoundException {
        return roleRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Not found role with ID=" + theId));
    }

    @Override
    public Role findByCode(String code) {
        return roleRepository.findByCode(code);
    }
}
