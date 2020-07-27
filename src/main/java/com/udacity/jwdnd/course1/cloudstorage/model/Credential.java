package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
  private Integer id;
  private String url;
  private String username;
  private String passKey;
  private String password;
  private Integer userId;
  private String rawPassword;

  public Credential(Integer id, String url, String username, String passKey, String password, Integer userId) {
    this.id = id;
    this.url = url;
    this.passKey = passKey;
    this.username = username;
    this.password = password;
    this.userId = userId;
  }

  public static Credential fromForm(CredentialForm form) {
    var cred = new Credential(form.getIdToInt(), form.getUrl(), form.getUsername(), null, null, null);
    cred.setRawPassword(form.getPassword());
    return cred;
  }

  public static Credential fromId(Integer id) {
    return new Credential(id, null, null, null, null, null);
  }

  public Credential setEncryptionKeyAndPassword(String passKey, String password) {
    this.passKey = passKey;
    this.password = password;
    return this;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassKey() {
    return passKey;
  }

  public void setPassKey(String passKey) {
    this.passKey = passKey;
  }

  public String getRawPassword() {
    return rawPassword;
  }

  public void setRawPassword(String rawPassword) {
    this.rawPassword = rawPassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getUserId() {
    return userId;
  }

  public Credential setUserId(Integer userId) {
    this.userId = userId;
    return this;
  }
}
