package com.udacity.jwdnd.course1.cloudstorage.model;

import com.udacity.jwdnd.course1.cloudstorage.validator.PasswordRetype;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordRetype
public class SignupForm {
  @NotNull
  @NotEmpty
  @Size(max = 20)
  private String username;

  @NotNull
  @NotEmpty
  @Size(max = 20)
  private String password;

  @NotNull
  @NotEmpty
  @Size(max = 20)
  private String retypePassword;

  @NotNull
  @NotEmpty
  @Size(max = 20)
  private String firstName;

  @Size(max = 20)
  private String lastName;

  public SignupForm() {
    this("", "", "", "", "");
  }

  public SignupForm(String username, String password, String retypePassword, String firstName, String lastName) {
    this.username = username;
    this.password = password;
    this.retypePassword = retypePassword;
    this.firstName = firstName;
    this.lastName = lastName;
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

  public String getRetypePassword() {
    return retypePassword;
  }

  public void setRetypePassword(String retypePassword) {
    this.retypePassword = retypePassword;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "SignupForm{" +
        "username='" + username + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }
}
