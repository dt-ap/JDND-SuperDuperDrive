package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {
  @FindBy(id = "firstName")
  private WebElement firstNameInput;

  @FindBy(id = "lastName")
  private WebElement lastNameInput;

  @FindBy(id = "username")
  private WebElement usernameInput;

  @FindBy(id = "password")
  private WebElement passwordInput;

  @FindBy(id = "retypePassword")
  private WebElement retypePasswordInput;

  @FindBy(id = "signupButton")
  private WebElement signupButton;

  private final WebDriverWait wait;

  public SignupPage(WebDriver driver, WebDriverWait wait) {
    PageFactory.initElements(driver, this);
    this.wait = wait;
  }

  public void signup(String firstName, String lastName, String username, String password) {
    wait.until(ExpectedConditions.visibilityOfAllElements(firstNameInput, usernameInput, signupButton));
    firstNameInput.sendKeys(firstName);
    lastNameInput.sendKeys(lastName);
    usernameInput.sendKeys(username);
    passwordInput.sendKeys(password);
    retypePasswordInput.sendKeys(password);
    signupButton.click();
  }
}
