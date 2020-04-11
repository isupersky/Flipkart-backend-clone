package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.co.PasswordCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.enitity.user.ConfirmationToken;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.exception.UserNotFoundException;
import com.tothenew.bluebox.bluebox.repository.ConfirmationTokenRepository;
import com.tothenew.bluebox.bluebox.repository.ProductRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  TaskExecutor taskExecutor;

  @Autowired
  ConfirmationTokenRepository confirmationTokenRepository;

  @Autowired
  EmailSenderService emailSenderService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private TokenStore tokenStore;


  /*
    Returns a List of all Product sorted by Id
   */
  public List<Product> returnProductList() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
    return productRepository.findAll(pageable).toList();
  }


  /*
    Generates a random token for a valid activated user and mail it to the user
   */
  public ResponseEntity<MessageResponseEntity> forgotPassword(String email) {

    User user = userRepository.findByEmailIgnoreCase(email);
    System.out.println(user);
    if (user == null) {
//      send not found
      throw new UserNotFoundException("User Not Found");
    } else if (user != null && !user.isActive()) {
//      User not active
      return new ResponseEntity<>(
          new MessageResponseEntity<>(HttpStatus.OK, "Please Activate Your Account First"),
          HttpStatus.OK);
    } else if (user != null && user.isActive()) {
//  generate token and send mail
      ConfirmationToken confirmationToken = new ConfirmationToken(user);
      if (confirmationTokenRepository.findAllByUserId(user.getId()) != null) {
        confirmationTokenRepository
            .findAllByUserId(user.getId())
            .forEach(e -> confirmationTokenRepository.deleteById(e.getTokenid()));
      }

      confirmationTokenRepository.save(confirmationToken);

//      Mail sender Service Thread
      taskExecutor.execute(() -> {
        try {
          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setTo(email);
          mailMessage.setSubject("Reset Password ");
          mailMessage.setFrom("ecommerce476@gmail.com ");
          mailMessage.setText("Use this link to reset your password :" +
              "http://localhost:8080/resetpassword/" + confirmationToken
              .getConfirmationToken());

          emailSenderService.sendEmail(mailMessage);
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println(
              "Failed to send email to: " + email + " reason: " + e.getMessage());
        }
      });

      return new ResponseEntity<>(
          new MessageResponseEntity<>(HttpStatus.OK, "Reset password Email sent"), HttpStatus.OK);
    }

    return new ResponseEntity<>(new MessageResponseEntity<>(HttpStatus.BAD_REQUEST),
        HttpStatus.BAD_REQUEST);
  }


  /*
    Updates User's password
   */
  public ResponseEntity<MessageResponseEntity> resetPassword(PasswordCO passwordCO, String token) {
    ConfirmationToken confirmationToken = confirmationTokenRepository
        .findByConfirmationToken(token);

    if (confirmationToken == null) {
      return new ResponseEntity<>(new MessageResponseEntity(HttpStatus.NOT_FOUND, "INVALID LINK"),
          HttpStatus.NOT_FOUND);
    } else if (passwordCO.getPassword().equals(passwordCO.getRePassword())) {
      Optional<User> user = userRepository.findById(confirmationToken.getUser().getId());
      user.get().setPassword(passwordEncoder.encode(passwordCO.getPassword()));
      user.get().setUpdatedDate(new Date());
      userRepository.save(user.get());
      confirmationTokenRepository.delete(confirmationToken);
      return new ResponseEntity<>(new MessageResponseEntity(HttpStatus.OK), HttpStatus.OK);

    }
    return new ResponseEntity<>(new MessageResponseEntity(HttpStatus.BAD_REQUEST),
        HttpStatus.BAD_REQUEST);
  }

  /*
    Logged in user is logged out
   */
  public ResponseEntity<MessageResponseEntity> doLogout(String authHeader) {
    if (authHeader != null) {
      String tokenValue = authHeader.replace("bearer", "").trim();
      OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
      tokenStore.removeAccessToken(accessToken);
    }
    return new ResponseEntity<>(
        new MessageResponseEntity<>(HttpStatus.OK, "Logged out successfully"), HttpStatus.OK);
  }
}
