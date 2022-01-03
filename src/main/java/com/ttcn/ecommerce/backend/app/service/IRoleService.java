package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.entity.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> findAll();

    public Role findById(Long theId);

    public Role findByCode(String code);
}
