package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.exception.CredentialException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

  private final CredentialMapper mapper;
  private final UserService userService;
  private final EncryptionService encryptionService;

  public CredentialService(CredentialMapper mapper, UserService userService, EncryptionService encryptionService) {
    this.mapper = mapper;
    this.userService = userService;
    this.encryptionService = encryptionService;
  }

  public List<Credential> getCredentialsByCurrentUser() {
    return mapper.findCredentialsByUserId(userService.getCurrentUser().getUserId()).stream()
        .peek(credential -> credential.setRawPassword(encryptionService.decryptValue(credential.getPassword(), credential.getPassKey())))
        .collect(Collectors.toList());
  }

  public int create(Credential credential) throws CredentialException {
    try {
      var newCredential = encryptCredential(credential);
      return mapper.insert(newCredential);
    } catch (DuplicateKeyException ex) {
      throw new CredentialException("'" + credential.getUrl() + "' with '" + credential.getUsername() +"' already exists.");
    }
  }

  public int update(Credential credential) {
    var newCredential = encryptCredential(credential);
    return mapper.update(newCredential);
  }

  public int delete(Integer id) throws CredentialException {
    var cred = Credential.fromId(id).setUserId(userService.getCurrentUser().getUserId());
    var deleted = mapper.delete(cred);
    if (deleted <= 0) {
      throw new CredentialException("There was an error when deleting credential");
    }

    return deleted;
  }

  private String createKey() {
    var random = new SecureRandom();
    var salt = new byte[16];
    random.nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }

  private Credential encryptCredential(Credential cred) {
    var key = createKey();
    var encryptedPassword = encryptionService.encryptValue(cred.getRawPassword(), key);
    return cred.setUserId(userService.getCurrentUser().getUserId())
        .setEncryptionKeyAndPassword(key, encryptedPassword);
  }

}
