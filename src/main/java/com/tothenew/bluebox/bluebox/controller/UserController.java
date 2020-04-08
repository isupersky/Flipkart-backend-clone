package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.dto.EmailDto;
import com.tothenew.bluebox.bluebox.dto.PasswordDto;
import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private UserService userService;

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
  public ResponseEntity<Object> userLogout(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null) {
      String tokenValue = authHeader.replace("bearer", "").trim();
      OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
      tokenStore.removeAccessToken(accessToken);
    }
    return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
  }


  /*
    URI for forget password
    @response - email with a token
   */
  @PostMapping(path = "/forgotpassword")
  public ResponseEntity<Object> forgotPassword(@Valid @RequestBody EmailDto emailDto) {
    return userService.forgotPassword(emailDto.getEmail());
  }


  /*
    URI to reset password
   */
  @PatchMapping(path = "/resetpassword/{token}")
  public ResponseEntity<Object> resetPassword(@Valid @RequestBody PasswordDto passwordDto,
      @PathVariable String token) {
    return userService.resetPassword(passwordDto, token);

  }

}
