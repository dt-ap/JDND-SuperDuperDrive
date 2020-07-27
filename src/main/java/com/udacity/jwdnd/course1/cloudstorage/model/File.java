package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class File {
  private Integer id;
  private String name;
  private String contentType;
  private String size;
  private Integer userId;
  private byte[] data;

  public File(Integer id, String name, String contentType, String size, Integer userId, byte[] data) {
    this.id = id;
    this.name = name;
    this.contentType = contentType;
    this.size = size;
    this.userId = userId;
    this.data = data;
  }

  public static File fromMultipart(MultipartFile file) throws IOException {
    return new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), null, file.getBytes());
  }

  public static File fromId(Integer id) {
    return new File(id, null, null, null, null, null);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public Integer getUserId() {
    return userId;
  }

  public File setUserId(Integer userId) {
    this.userId = userId;
    return this;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
