package com.tothenew.bluebox.bluebox.security;


import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import com.tothenew.bluebox.bluebox.service.EmailSenderService;
import com.tothenew.bluebox.bluebox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;


public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  UserDetailsService userDetailsService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private UserService userService;

  @Autowired
  private TaskExecutor taskExecutor;

  @Autowired
  private EmailSenderService emailSenderService;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    if (authentication.getCredentials() == null) {
      logger.debug("Authentication failed: no credentials provided");

      throw new BadCredentialsException(messages.getMessage(
          "AbstractUserDetailsAuthenticationProvider.badCredentials",
          "Bad credentials"));
    }

    String presentedPassword = authentication.getCredentials().toString();

    if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
      logger.debug("Authentication failed: password does not match stored value");

      User user = userRepository.findByEmailIgnoreCase(userDetails.getUsername());
      Integer temp = user.getFalseAttemptCount();
      user.setFalseAttemptCount(++temp);
      if (temp == 2) {

        user.setLocked(true);
//        cli tokenStore.findTokensByClientId(user.getId().toString());
//        tokenStore.removeAccessToken();

        taskExecutor.execute(() -> {
          try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Account Locked");
            mailMessage.setFrom("ecommerce476@gmail.com ");
            mailMessage.setText("Your Account has been locked due to multiple false attempts. "
                + "Please contact Admin (ecommerce476@gmail.com) to get back your account");

            emailSenderService.sendEmail(mailMessage);
          } catch (Exception e) {
            e.printStackTrace();
            System.err.println(
                "Failed to send email to: " + user.getEmail() + " reason: " + e.getMessage());
          }
        });

      }
      userRepository.save(user);

      throw new BadCredentialsException(
          "Bad credentials");
    }

  }

  @Override
  protected Authentication createSuccessAuthentication(Object principal,
      Authentication authentication, UserDetails user) {
    User user1 = userRepository.findByEmailIgnoreCase(user.getUsername());
    ;
    user1.setFalseAttemptCount(0);
    userRepository.save(user1);
    return super.createSuccessAuthentication(principal, authentication, user);
  }
}
