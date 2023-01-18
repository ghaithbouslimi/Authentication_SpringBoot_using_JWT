package com.Autthenitification.AuthentificationJwt.service;

import com.Autthenitification.AuthentificationJwt.dao.RoleDao;
import com.Autthenitification.AuthentificationJwt.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleService {
    @Autowired
    private RoleDao roleDao ;
    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}
