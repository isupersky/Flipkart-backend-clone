package com.tothenew.bluebox.bluebox.security;


import com.tothenew.bluebox.bluebox.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


  @Autowired
  UserDetailsServiceImpl userDetailsService;

  public ResourceServerConfiguration() {
    super();
  }

  @Bean
  public static BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
    return authenticationProvider;
  }

  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
    authenticationManagerBuilder.authenticationProvider(authenticationProvider());
  }

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/").anonymous()
        .antMatchers("/customer/register", "/customer/confirm-account", "/seller/register",
            "/forgotpassword", "/resetpassword", "/username").permitAll()
        .antMatchers("/logout").hasAnyRole("ADMIN", "SELLER", "CUSTOMER")
        .antMatchers("/admin/*").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .csrf().disable();
  }
}
