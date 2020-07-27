package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
  private Integer id;
  private String title;
  private String description;
  private Integer userId;

  public Note(Integer id, String title, String description, Integer userId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.userId = userId;
  }

  public static Note fromForm(NoteForm form) {
    return new Note(form.getIdToInt(), form.getTitle(), form.getDescription(), null);
  }

  public static Note fromId(Integer id) {
    return new Note(id, null, null, null);
  }

  public Note setUserId(Integer userId) {
    this.userId = userId;
    return this;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public Integer getUserId() {
    return userId;
  }

}
