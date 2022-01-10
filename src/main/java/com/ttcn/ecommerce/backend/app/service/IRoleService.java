package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.dto.RoleDTO;
import com.ttcn.ecommerce.backend.app.entity.Role;

import java.util.List;

public interface IRoleService {

    List<Role> findAll();

    Role findById(Long theId);

    Role findByCode(String code);

    MessageResponse createRole(RoleDTO theRoleDto);

    MessageResponse updateRole(Long theId, RoleDTO theRoleDto);

    void deleteProduct(Long theId);
}
