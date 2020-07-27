package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.CredentialException;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

  private static final String credFormFlash = "credForm";
  private static final String credSuccessFlash = "credSuccess";
  private static final String credErrorFlash = "credError";
  private static final String credRedirect = "redirect:/home#credentials";

  private final CredentialService service;

  public CredentialController(CredentialService service) {
    this.service = service;
  }

  @PostMapping
  public String addOrEditCredential(@Valid @ModelAttribute CredentialForm form, BindingResult result, RedirectAttributes redirectAttr) {
    try {
      String success;
      if (result.hasErrors()) {
        throw new CredentialException("Please input the correct entries.");
      }

      var credential = Credential.fromForm(form);
      if (form.isCreateCredential()) {
        var created = service.create(credential);
        if (created < 0) {
          redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult." + credFormFlash, result);
          redirectAttr.addFlashAttribute(credFormFlash, form);
          throw new CredentialException("There was an error when creating new credential.");
        }
        success = "Credential created successfully!";
      } else {
        var updated = service.update(credential);
        if (updated <= 0) {
          throw new CredentialException("There was an error when updating credential.");
        }
        success = "Credential updated successfully!";
      }
      redirectAttr.addFlashAttribute(credSuccessFlash, success);

    } catch (CredentialException ex) {
      redirectAttr.addFlashAttribute(credErrorFlash, ex.getMessage());
    }

    return credRedirect;
  }

  @PostMapping("/delete/{id}")
  public String deleteCredential(@PathVariable Integer id, RedirectAttributes redirectAttr) {
    try {
      service.delete(id);
      redirectAttr.addFlashAttribute(credSuccessFlash, "Credential deleted successfully!");
    } catch (CredentialException ex) {
      redirectAttr.addFlashAttribute(credErrorFlash, ex.getMessage());
    }

    return credRedirect;
  }
}
