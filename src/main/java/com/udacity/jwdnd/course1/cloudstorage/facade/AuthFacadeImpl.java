package com.udacity.jwdnd.course1.cloudstorage.facade;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {

  @Override
  public Authentication getAuth() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
