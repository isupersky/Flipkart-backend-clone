package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.co.AddressCO;
import com.tothenew.bluebox.bluebox.co.PasswordCO;
import com.tothenew.bluebox.bluebox.co.PasswordResetCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.enitity.user.Address;
import com.tothenew.bluebox.bluebox.enitity.user.ConfirmationToken;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.exception.AccessNotAllowedExeption;
import com.tothenew.bluebox.bluebox.exception.UserNotFoundException;
import com.tothenew.bluebox.bluebox.repository.AddressRepository;
import com.tothenew.bluebox.bluebox.repository.ConfirmationTokenRepository;
import com.tothenew.bluebox.bluebox.repository.ProductRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TaskExecutor taskExecutor;

  @Autowired
  private ConfirmationTokenRepository confirmationTokenRepository;

  @Autowired
  private EmailSenderService emailSenderService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private ImageUploaderService imageUploaderService;

  @Autowired
  private MessageSource messageSource;


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

      String messageSubject = messageSource.getMessage("password.reset.subject",
          null,
          LocaleContextHolder
              .getLocale());
      String messageText = messageSource.getMessage("password.reset.message",
          null,
          LocaleContextHolder
              .getLocale());
//      Mail sender Service Thread
      taskExecutor.execute(() -> {
        try {
          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setTo(email);
          mailMessage.setSubject(messageSubject);
          mailMessage.setFrom("ecommerce476@gmail.com ");
          mailMessage.setText(messageText +
              "http://localhost:8080/user/reset-password/" + confirmationToken
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
  public ResponseEntity<MessageResponseEntity> resetPassword(PasswordResetCO passwordCO,
      String token) {
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


  /*
    Updates already saved address
   */
  public ResponseEntity<MessageResponseEntity> updateAddress(String email, Long addressId,
      AddressCO addressCO) {
    Optional<Address> optionalAddress = addressRepository.findById(addressId);
    User user = userRepository.findByEmailIgnoreCase(email);

    if (optionalAddress.isPresent()) {
      Address address = optionalAddress.get();
      if (address.getUser().getId() == user.getId()) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(addressCO, address);
        addressRepository.save(address);

        return new ResponseEntity<>(
            new MessageResponseEntity(addressCO, HttpStatus.OK)
            , HttpStatus.OK
        );
      } else if (address.getUser().getId() != user.getId()) {
        throw new AccessNotAllowedExeption("Invalid Request");
      }


    } else {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.NOT_FOUND, "invalid address id".toUpperCase())
          , HttpStatus.NOT_FOUND
      );
    }

    return new ResponseEntity<>(
        new MessageResponseEntity(HttpStatus.OK)
        , HttpStatus.OK
    );
  }

  /*
   Updates User password and log them out.
  */
  public ResponseEntity<MessageResponseEntity> updatePassword(String authHeader,
      PasswordCO passwordCO, String email) {

    User user = userRepository.findByEmailIgnoreCase(email);
    if (passwordEncoder.matches(passwordCO.getOldPassword(), user.getPassword())) {

      if (passwordCO.getPassword().equals(passwordCO.getRePassword())) {
        user.setPassword(passwordEncoder.encode(passwordCO.getPassword()));
        user.setUpdatedDate(new Date());
        userRepository.save(user);
        doLogout(authHeader);

        String messageSubject = messageSource.getMessage("successful.password.reset.subject",
            null,
            LocaleContextHolder
                .getLocale());
        String messageText = messageSource.getMessage("successful.password.reset.message",
            null,
            LocaleContextHolder
                .getLocale());
        taskExecutor.execute(() -> {
          try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(messageSubject);
            mailMessage.setFrom("ecommerce476@gmail.com ");
            mailMessage.setText(messageText);

            emailSenderService.sendEmail(mailMessage);
          } catch (Exception e) {
            e.printStackTrace();
            System.err.println(
                "Failed to send email to: " + email + " reason: " + e.getMessage());
          }
        });

        return new ResponseEntity<>(
            new MessageResponseEntity(HttpStatus.OK,
                "Password Changed Successfully. Please reLogin".toUpperCase())
            , HttpStatus.OK);

      }
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.BAD_REQUEST,
              "password and confirm password should be same".toUpperCase()),
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
        new MessageResponseEntity(HttpStatus.BAD_REQUEST,
            "Old password Incorrect".toUpperCase()),
        HttpStatus.BAD_REQUEST);
  }

  /*
    Upload Profile image
   */
  public ResponseEntity<MessageResponseEntity> uploadProfileImage(MultipartFile multipartFile,
      String email) {
    User user = userRepository.findByEmailIgnoreCase(email);
    try {
      String imageUri = imageUploaderService.uploadUserImage(multipartFile, email);
      user.setUserDp(imageUri);
      userRepository.save(user);
      return new ResponseEntity<>(
          new MessageResponseEntity<>(imageUri, HttpStatus.OK)
          , HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new MessageResponseEntity<>(HttpStatus.BAD_REQUEST, "Try again")
          , HttpStatus.BAD_REQUEST);
    }
  }

  /*
   Upload Profile image
  */
  public ResponseEntity<MessageResponseEntity> getProfileImage(String email) {
    User user = userRepository.findByEmailIgnoreCase(email);
    if (user.getUserDp() != null) {
      return new ResponseEntity<>(
          new MessageResponseEntity(user.getUserDp(), HttpStatus.OK)
          , HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new MessageResponseEntity(HttpStatus.BAD_REQUEST)
        , HttpStatus.BAD_REQUEST);
  }
}
