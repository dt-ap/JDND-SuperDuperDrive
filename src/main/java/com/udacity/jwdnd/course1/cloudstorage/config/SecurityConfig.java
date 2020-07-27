package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.service.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final AuthenticationService authService;

  public SecurityConfig(AuthenticationService authService) {
    this.authService = authService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/css/**", "/js/**").permitAll()
        .antMatchers("/signup", "/signup/").anonymous()
        .antMatchers("/login", "/login/").anonymous()
        .anyRequest().authenticated()
        .and().formLogin().loginPage("/login").defaultSuccessUrl("/home", true)
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID");
  }
}
