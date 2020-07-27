package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.UserException;
import com.udacity.jwdnd.course1.cloudstorage.model.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignupController {
  private static final String formFlash = "signupForm";
  private static final String signupSuccessFlash = "signupSuccess";
  private static final String signupErrorFlash = "signupError";

  private final UserService userService;

  public SignupController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String signupPage(Model model) {
    if (!model.containsAttribute(formFlash)) {
      model.addAttribute(formFlash, new SignupForm());
    }
    return "signup";
  }

  @PostMapping
  public String signupUser(@Valid @ModelAttribute SignupForm form, BindingResult result, RedirectAttributes redirectAttr) {
    try {
      if (result.hasErrors()) {
        throw new UserException("Please input the correct entries.");
      }
      int rowsAdded = userService.createUser(form);
      if (rowsAdded < 0) {
        throw new UserException("There was an error signing you up. Please try again.");
      }
      redirectAttr.addFlashAttribute(signupSuccessFlash, true);

    } catch (UserException ex) {
      form.setPassword("");
      form.setRetypePassword("");
      redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult." + formFlash, result);
      redirectAttr.addFlashAttribute(formFlash, form);
      redirectAttr.addFlashAttribute(signupErrorFlash, ex.getMessage());
      return "redirect:signup";
    }

    return "redirect:/login";
  }
}
