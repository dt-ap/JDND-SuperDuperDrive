package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileException;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {
  private static final String fileFlash = "fileFlash";
  private static final String fileSuccessFlash = "fileSuccess";
  private static final String fileErrorFlash = "fileError";
  private static final String fileRedirect = "redirect:/home";

  private final FileService service;

  public FileController(FileService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getFile(@PathVariable Integer id) {
    try {
      var file = service.getFileById(id);
      var contentDesc = "attachment; filename=\"" + file.getName() + "\"";
      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(file.getContentType()))
          .header(HttpHeaders.CONTENT_DISPOSITION, contentDesc)
          .body(file.getData());
    } catch (FileException ex) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public String addFile(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttr) {
    try {
      if (file.isEmpty()) {
        throw new FileException("Please attach file.");
      }
      var newFile = File.fromMultipart(file);
      service.create(newFile);
      redirectAttr.addFlashAttribute(fileSuccessFlash, "File uploaded successfully");
    } catch (IOException ex) {
      redirectAttr.addFlashAttribute(fileErrorFlash, "Something's wrong when processing file.");
    } catch (FileException ex) {
      redirectAttr.addFlashAttribute(fileErrorFlash, ex.getMessage());
    }

    return fileRedirect;
  }

  @PostMapping("/delete/{id}")
  public String deleteFile(@PathVariable Integer id, RedirectAttributes redirectAttr) {
    try {
      service.delete(id);
      redirectAttr.addFlashAttribute(fileSuccessFlash, "File Delete Successfully.");
    } catch (FileException ex) {
      redirectAttr.addFlashAttribute(fileErrorFlash, ex.getMessage());
    }

    return fileRedirect;
  }
}
