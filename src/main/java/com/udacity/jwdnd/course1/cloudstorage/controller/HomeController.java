package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

  private final CredentialService credentialService;
  private final FileService fileService;
  private final NoteService noteService;

  public HomeController(CredentialService credentialService, FileService fileService, NoteService noteService) {
    this.credentialService = credentialService;
    this.fileService = fileService;
    this.noteService = noteService;
  }

  @GetMapping
  public String homePage(Model model) {
    model.addAttribute("credentials", credentialService.getCredentialsByCurrentUser());
    model.addAttribute("files", fileService.getFilesByCurrentUser());
    model.addAttribute("notes", noteService.getNotesByCurrentUser());

    return "home";
  }

}
