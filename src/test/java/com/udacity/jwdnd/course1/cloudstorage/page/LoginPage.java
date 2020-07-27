package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

  @FindBy(id = "username")
  private WebElement usernameInput;

  @FindBy(id = "password")
  private WebElement passwordInput;

  @FindBy(id = "loginButton")
  private WebElement loginButton;

  private final WebDriverWait wait;

  public LoginPage(WebDriver driver, WebDriverWait wait) {
    PageFactory.initElements(driver, this);
    this.wait = wait;
  }

  public void login(String username, String password) {
    wait.until(ExpectedConditions.visibilityOfAllElements(usernameInput, passwordInput, loginButton));
    usernameInput.sendKeys(username);
    passwordInput.sendKeys(password);
    loginButton.click();
  }
}
