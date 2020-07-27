package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
  @FindBy(id = "logoutButton")
  private WebElement logoutButton;

  @FindBy(id = "nav-credentials-tab")
  private WebElement credentialTab;

  private final WebDriverWait wait;

  public HomePage(WebDriver driver, WebDriverWait wait) {
    PageFactory.initElements(driver, this);
    this.wait = wait;
  }

  public void logout() {
    wait.until(ExpectedConditions.visibilityOf(logoutButton));
    logoutButton.click();
  }
}
