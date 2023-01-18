package com.Autthenitification.AuthentificationJwt.dao;

import com.Autthenitification.AuthentificationJwt.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, String> {
}
