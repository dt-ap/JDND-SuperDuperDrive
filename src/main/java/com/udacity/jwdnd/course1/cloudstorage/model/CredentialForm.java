package com.udacity.jwdnd.course1.cloudstorage.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CredentialForm {
  private String id;

  @NotNull
  @NotEmpty
  @Size(max = 100)
  private String url;

  @NotNull
  @NotEmpty
  @Size(max = 30)
  private String username;

  @NotNull
  @NotEmpty
  @Size(max = 30)
  private String password;

  public CredentialForm() {
    this("", "", "", "");
  }

  public CredentialForm(String id, String url, String username, String password) {
    this.id = id;
    this.url = url;
    this.username = username;
    this.password = password;
  }

  public boolean isCreateCredential() {
    return id.isEmpty() && id.isBlank();
  }

  public String getId() {
    return id;
  }

  public Integer getIdToInt() {
    if (isCreateCredential()) {
      return null;
    }
    return Integer.parseInt(id);
  }

  public void setId(String id) {
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
