package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
  private UserMapper userMapper;
  private HashService hashService;

  public AuthenticationService(UserMapper userMapper, HashService hashService) {
    this.userMapper = userMapper;
    this.hashService = hashService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    var username = authentication.getName();
    var password = authentication.getCredentials().toString();

    var user = userMapper.getUser(username);
    if (user != null) {
      var encodedSalt = user.getSalt();
      var hashedPassword = hashService.getHashedValue(password, encodedSalt);
      if (user.getPassword().equals(hashedPassword)) {
        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
      }
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return aClass.equals(UsernamePasswordAuthenticationToken.class);
  }
}