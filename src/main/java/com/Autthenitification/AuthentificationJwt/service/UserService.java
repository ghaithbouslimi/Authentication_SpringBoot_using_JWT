package com.Autthenitification.AuthentificationJwt.service;

import com.Autthenitification.AuthentificationJwt.dao.RoleDao;
import com.Autthenitification.AuthentificationJwt.dao.UserDao;
import com.Autthenitification.AuthentificationJwt.entity.Role;
import com.Autthenitification.AuthentificationJwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void  initRoleAndUser(){
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName(("User"));
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("ghaithBS");
        adminUser.setUserPassword(getEncodedPassword("ghaith@BS"));
        adminUser.setUserFirstName("ghaith");
        adminUser.setUserLastName("bouslimi");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

    }
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
    }
}
