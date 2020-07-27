package com.udacity.jwdnd.course1.cloudstorage.facade;

import org.springframework.security.core.Authentication;

public interface AuthFacade {
  Authentication getAuth();
}
