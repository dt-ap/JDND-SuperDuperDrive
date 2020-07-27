package com.udacity.jwdnd.course1.cloudstorage.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoteForm {
  private String id;

  @NotNull
  @NotEmpty
  @Size(max = 20)
  private String title;

  @NotNull
  @NotEmpty
  @Size(max = 1000)
  private String description;

  public NoteForm() {
    this("", "", "");
  }

  public NoteForm(String id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
  }

  public boolean isCreateNote() {
    return id.isEmpty() && id.isBlank();
  }

  public String getId() {
    return id;
  }

  public Integer getIdToInt() {
    if (isCreateNote()) {
      return null;
    }
    return Integer.parseInt(id);
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
