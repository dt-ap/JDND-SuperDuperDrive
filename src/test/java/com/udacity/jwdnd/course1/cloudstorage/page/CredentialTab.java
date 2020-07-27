package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class CredentialTab {
  @FindBy(id = "nav-credentials-tab")
  private WebElement navTab;

  @FindBy(id = "nav-credentials")
  private WebElement nav;

  @FindBy(id = "credSuccessSpan")
  private WebElement successSpan;

  @FindBy(id = "credErrorSpan")
  private WebElement errorSpan;

  @FindBy(id = "addCredModalButton")
  private WebElement addModalButton;

  @FindBy(id = "credentialModal")
  private WebElement modal;

  @FindBy(id = "credUrlInput")
  private WebElement urlInput;

  @FindBy(id = "credUsernameInput")
  private WebElement usernameInput;

  @FindBy(id = "credPasswordInput")
  private WebElement passwordInput;

  @FindBy(id = "credModalSaveButton")
  private WebElement modalSaveButton;

  @FindBy(className = "credRows")
  private List<WebElement> rows;

  @FindBy(className = "credEditButton")
  private List<WebElement> editButtons;

  @FindBy(className = "credDeleteButton")
  private List<WebElement> deleteButtons;

  @FindBy(className = "credUrlRow")
  private List<WebElement> urlRows;

  @FindBy(id = "deleteModal")
  private WebElement deleteModal;

  @FindBy(id = "confirmDeleteButton")
  private WebElement confirmDeleteButton;

  private final WebDriverWait wait;

  public CredentialTab(WebDriver driver, WebDriverWait wait) {
    PageFactory.initElements(driver, this);
    this.wait = wait;
  }

  public Integer getRowsCount() {
    return rows.size();
  }

  public void clickNavTab() {
    wait.until(ExpectedConditions.visibilityOf(navTab));
    navTab.click();
  }

  public void add(String url, String username, String password) {
    wait.until(ExpectedConditions.visibilityOf(addModalButton));
    addModalButton.click();
    wait.until(ExpectedConditions.visibilityOf(modal));
    urlInput.sendKeys(url);
    usernameInput.sendKeys(username);
    passwordInput.sendKeys(password);
    modalSaveButton.click();
    wait.until(ExpectedConditions.or(
        ExpectedConditions.visibilityOf(successSpan),
        ExpectedConditions.visibilityOf(errorSpan)
    ));
  }

  public void edit(Integer id, String url, String username, String password) {
    var editButton = getEditButton(id);
    editButton.click();
    wait.until(ExpectedConditions.visibilityOf(modal));
    if (url != null) {
      urlInput.clear();
      urlInput.sendKeys(url);
    }
    if (username != null) {
      usernameInput.clear();
      usernameInput.sendKeys(username);
    }
    if (password != null) {
      passwordInput.clear();
      passwordInput.sendKeys(password);
    }
    modalSaveButton.click();
    wait.until(ExpectedConditions.or(
        ExpectedConditions.visibilityOf(successSpan),
        ExpectedConditions.visibilityOf(errorSpan)
    ));
  }

  public void delete(Integer id) {
    var deleteButton = getDeleteButton(id);
    deleteButton.click();
    wait.until(ExpectedConditions.visibilityOf(deleteModal));
    confirmDeleteButton.click();
    wait.until(ExpectedConditions.or(
        ExpectedConditions.visibilityOf(successSpan),
        ExpectedConditions.visibilityOf(errorSpan)
    ));
  }

  public String getSuccessSpan() {
    return successSpan.getText();
  }

  public boolean isUrlExists(String title) {
    wait.until(ExpectedConditions.visibilityOfAllElements(urlRows));
    return urlRows.stream().anyMatch(el -> {
      return el.getText().equals(title);
    });
  }

  private WebElement getEditButton(Integer id) throws NoSuchElementException {
    wait.until(ExpectedConditions.visibilityOfAllElements(editButtons));
    return editButtons.stream().filter(el -> el.getAttribute("data-id").equals(id.toString()))
        .findFirst().orElseThrow();
  }

  private WebElement getDeleteButton(Integer id) throws NoSuchElementException {
    wait.until(ExpectedConditions.visibilityOfAllElements(deleteButtons));
    return deleteButtons.stream().filter(el -> el.getAttribute("data-action").equals("/credentials/delete/" + id))
        .findFirst().orElseThrow();
  }

}
