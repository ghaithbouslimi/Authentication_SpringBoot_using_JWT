package com.Autthenitification.AuthentificationJwt.service;

import com.Autthenitification.AuthentificationJwt.dao.UserDao;
import com.Autthenitification.AuthentificationJwt.entity.JwtRequest;
import com.Autthenitification.AuthentificationJwt.entity.JwtResponse;
import com.Autthenitification.AuthentificationJwt.util.JwtUtil;
import com.Autthenitification.AuthentificationJwt.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class JwtService implements UserDetailsService {
    @Autowired
    private JwtUtil jwtUtil ;
    @Autowired
    private UserDao userDao ;
    @Autowired
    private AuthenticationManager authenticationManager ;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String username = jwtRequest.getUserName();
        String password = jwtRequest.getUserPassword();
        authenticate(username , password );

        UserDetails userDetails = loadUserByUsername(username);
        String newGeneratedToken =  jwtUtil.generateToken(userDetails);

        User user = userDao.findById(username).get();
        return new JwtResponse(user, newGeneratedToken);
    }
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User user = userDao.findById(username).get();

          if(user != null){
              return new org.springframework.security.core.userdetails.User(
                      user.getUserName(),
                      user.getUserPassword(),
                      getAuthority(user)
              );
          } else {
              throw new UsernameNotFoundException("User not found with username: " + username);
          }
   }
    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }


    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
