package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.CredentialException;
import com.udacity.jwdnd.course1.cloudstorage.exception.NoteException;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {
  private static final String noteFormFlash = "noteForm";
  private static final String noteSuccessFlash = "noteSuccess";
  private static final String noteErrorFlash = "noteError";
  private static final String noteRedirect = "redirect:/home#notes";

  private final NoteService service;

  public NoteController(NoteService service) {
    this.service = service;
  }

  @PostMapping
  public String addOrEditNote(@Valid @ModelAttribute NoteForm form, BindingResult result, RedirectAttributes redirectAttr) {
    try {
      String success;
      if (result.hasErrors()) {
        throw new NoteException("Please input the correct entries.");
      }

      var note = Note.fromForm(form);
      if (form.isCreateNote()) {
        var created = service.create(note);
        if (created < 0) {
          redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult." + noteFormFlash, result);
          redirectAttr.addFlashAttribute(noteFormFlash, form);
          throw new NoteException("There was an error when creating new note.");
        }
        success = "Note created successfully!";
      } else {
        var updated = service.update(note);
        if (updated <= 0) {
          throw new NoteException("There was an error when updating note.");
        }
        success = "Note updated successfully!";
      }
      redirectAttr.addFlashAttribute(noteSuccessFlash, success);

    } catch (NoteException ex) {
      redirectAttr.addFlashAttribute(noteErrorFlash, ex.getMessage());
    }

    return noteRedirect;
  }

  @PostMapping("/delete/{id}")
  public String deleteNote(@PathVariable Integer id, RedirectAttributes redirectAttr) {
    try {
      service.delete(id);
      redirectAttr.addFlashAttribute(noteSuccessFlash, "Note deleted successfully!");
    } catch (NoteException ex) {
      redirectAttr.addFlashAttribute(noteErrorFlash, ex.getMessage());
    }

    return noteRedirect;
  }
}
