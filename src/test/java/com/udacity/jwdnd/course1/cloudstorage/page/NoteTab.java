package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class NoteTab {
  @FindBy(id = "nav-notes-tab")
  private WebElement navTab;

  @FindBy(id = "nav-notes")
  private WebElement nav;

  @FindBy(id = "noteSuccessSpan")
  private WebElement successSpan;

  @FindBy(id = "noteErrorSpan")
  private WebElement errorSpan;

  @FindBy(id = "addNoteModalButton")
  private WebElement addModalButton;

  @FindBy(id = "noteModal")
  private WebElement modal;

  @FindBy(id = "noteTitleInput")
  private WebElement titleInput;

  @FindBy(id = "noteDescInput")
  private WebElement descInput;

  @FindBy(id = "noteModalSaveButton")
  private WebElement modalSaveButton;

  @FindBy(className = "noteRows")
  private List<WebElement> rows;

  @FindBy(className = "noteEditButton")
  private List<WebElement> editButtons;

  @FindBy(className = "noteDeleteButton")
  private List<WebElement> deleteButtons;

  @FindBy(className = "noteTitleRow")
  private List<WebElement> titleRows;

  @FindBy(id = "deleteModal")
  private WebElement deleteModal;

  @FindBy(id = "confirmDeleteButton")
  private WebElement confirmDeleteButton;

  private final WebDriverWait wait;

  public NoteTab(WebDriver driver, WebDriverWait wait) {
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

  public void add(String title, String description) {
    wait.until(ExpectedConditions.visibilityOf(addModalButton));
    addModalButton.click();
    wait.until(ExpectedConditions.visibilityOf(modal));
    titleInput.sendKeys(title);
    descInput.sendKeys(description);
    modalSaveButton.click();
    wait.until(ExpectedConditions.or(
        ExpectedConditions.visibilityOf(successSpan),
        ExpectedConditions.visibilityOf(errorSpan)
    ));
  }

  public void edit(Integer id, String title, String description) {
    var editButton = getEditButton(id);
    editButton.click();
    wait.until(ExpectedConditions.visibilityOf(modal));
    if (title != null) {
      titleInput.clear();
      titleInput.sendKeys(title);
    }
    if (description != null) {
      descInput.clear();
      descInput.sendKeys(description);
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

  public boolean isTitleExist(String title) {
    wait.until(ExpectedConditions.visibilityOfAllElements(titleRows));
    return titleRows.stream().anyMatch(el -> {
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
    return deleteButtons.stream().filter(el -> el.getAttribute("data-action").equals("/notes/delete/" + id))
        .findFirst().orElseThrow();
  }

}
