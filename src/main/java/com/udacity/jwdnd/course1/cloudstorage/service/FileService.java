package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

  private final FileMapper mapper;
  private final UserService userService;

  public FileService(FileMapper mapper, UserService userService) {
    this.mapper = mapper;
    this.userService = userService;
  }

  public List<File> getFilesByCurrentUser() {
    return mapper.findFilesByUserId(userService.getCurrentUser().getUserId());
  }

  public File getFileById(Integer id) {
    return mapper.findFilebyIdAndUserId(id, userService.getCurrentUser().getUserId());
  }

  public int create(File file) throws FileException {
    try {
      var newFile = file.setUserId(userService.getCurrentUser().getUserId());
      return mapper.insert(newFile);
    } catch (DuplicateKeyException ex) {
      throw new FileException("'" + file.getName() + "' already exists.");
    }
  }

  public int delete(Integer id) throws FileException {
    var file = File.fromId(id).setUserId(userService.getCurrentUser().getUserId());
    var deleted = mapper.delete(file);
    if (deleted <= 0) {
      throw new FileException("There was an error when deleting file.");
    }

    return deleted;
  }
}
