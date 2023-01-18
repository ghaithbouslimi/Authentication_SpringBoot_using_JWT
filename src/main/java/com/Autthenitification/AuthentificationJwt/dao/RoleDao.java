package com.Autthenitification.AuthentificationJwt.dao;

import com.Autthenitification.AuthentificationJwt.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role, String> {
}
