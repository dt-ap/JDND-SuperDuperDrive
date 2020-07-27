package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.exception.UserException;
import com.udacity.jwdnd.course1.cloudstorage.facade.AuthFacade;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

  private final UserMapper mapper;
  private final HashService hashService;
  private final AuthFacade authFacade;

  public UserService(UserMapper mapper, HashService hashService, AuthFacade authFacade) {
    this.mapper = mapper;
    this.hashService = hashService;
    this.authFacade = authFacade;
  }

  public boolean isUsernameAvailable(String username) {
    return mapper.getUser(username) == null;
  }

  public User getCurrentUser() {
    return mapper.getUser(authFacade.getAuth().getName());
  }

  public int createUser(SignupForm form) throws UserException {
    try {
      var random = new SecureRandom();
      var salt = new byte[16];
      random.nextBytes(salt);
      var encodedSalt = Base64.getEncoder().encodeToString(salt);
      var hashedPassword = hashService.getHashedValue(form.getPassword(), encodedSalt);
      return mapper.insert(User.fromSignup(form, encodedSalt, hashedPassword));
    } catch (DuplicateKeyException ex) {
      throw new UserException("Username '" + form.getUsername() + "' already exist.");
    }
  }

  public int deleteAll() {
    return mapper.deleteAll();
  }
}
