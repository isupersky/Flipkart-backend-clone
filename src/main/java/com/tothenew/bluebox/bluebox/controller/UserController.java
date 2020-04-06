package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


  @RequestMapping(path = "/username", method = RequestMethod.GET)
  @ResponseBody
  public String currentUserName(Principal principal) {

//    System.out.println(principal.toString());
    return principal.getName();

  }


  /*
    provides Logout functionality for all type of Users.
   */
  @PostMapping(path = "/dologout")
  public Object userLogout(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    System.out.println(authHeader);
    if (authHeader != null) {
      String tokenValue = authHeader.replace("bearer", "").trim();
      System.out.println(tokenValue);
      OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
      tokenStore.removeAccessToken(accessToken);
    }
    return "Logged out successfully";
  }


}
