package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.co.EmailCO;
import com.tothenew.bluebox.bluebox.co.PasswordResetCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import com.tothenew.bluebox.bluebox.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  CustomerService customerService;


  /*
    Returns the list of all product sorted by id for Home Page.
   */
  @GetMapping(path = "/")
  public List<Product> homePage() {
    return userService.returnProductList();
  }


  @GetMapping(path = "/username")
  @ResponseBody
  public String currentUserName(Principal principal) {

    return principal.getName();

  }


  /*
    provides Logout functionality for all type of Users.
   */
  @PostMapping(path = "/dologout")
  public ResponseEntity<MessageResponseEntity> userLogout(HttpServletRequest request) {

    String authHeader = request.getHeader("Authorization");
    return userService.doLogout(authHeader);
  }


  /*
    URI for forget password
    @response - email with a token
   */
  @PostMapping(path = "/forgot-password")
  public ResponseEntity<MessageResponseEntity> forgotPassword(@Valid @RequestBody EmailCO emailCO) {
    return userService.forgotPassword(emailCO.getEmail());
  }


  /*
    URI to reset password
   */
  @PatchMapping(path = "/reset-password/{token}")
  public ResponseEntity<MessageResponseEntity> resetPassword(
      @Valid @RequestBody PasswordResetCO passwordCO,
      @PathVariable String token) {
    return userService.resetPassword(passwordCO, token);

  }

  /* authorization error
    URI to resend Activation Token
   */
  @PostMapping(path = "/resend-activation")
  public ResponseEntity<MessageResponseEntity> resendActivationToken(
      @Valid @RequestBody EmailCO emailCO) {
    return customerService.resendActivationToken(emailCO.getEmail());
  }

  /*
    URI to add profile Image
   */
  @PostMapping(value = "/upload")
  public ResponseEntity<MessageResponseEntity> uploadProfileImage(
      @RequestParam(value = "upload", required = true) MultipartFile multipartFile,
      Principal principal) {

    String email = principal.getName();
    return userService.uploadProfileImage(multipartFile, email);
  }

  /*
    URI to Get profile Image
   */
  @GetMapping(value = "/profile-image")
  public ResponseEntity<MessageResponseEntity> getProfileImage(Principal principal) {

    String email = principal.getName();
    return userService.getProfileImage(email);
  }
}
