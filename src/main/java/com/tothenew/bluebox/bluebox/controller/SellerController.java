package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.co.AddressCO;
import com.tothenew.bluebox.bluebox.co.PasswordCO;
import com.tothenew.bluebox.bluebox.co.SellerCO;
import com.tothenew.bluebox.bluebox.co.SellerProfileUpdate;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.SellerService;
import com.tothenew.bluebox.bluebox.service.UserService;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/seller")
public class SellerController {

  @Autowired
  SellerService sellerService;

  @Autowired
  UserService userService;

//---------------------------------------------------CREATE------------------------------------------------------------

  /*
    URI for seller registration
   */
  @PostMapping(path = "/register")
  public ResponseEntity<MessageResponseEntity> register(@Valid @RequestBody SellerCO sellerCO) {
    return sellerService.registerSeller(sellerCO);
  }

//---------------------------------------------------READ------------------------------------------------------------

  /*
    URI to view selelr profile
   */
  @GetMapping(path = "/profile")
  public ResponseEntity<MessageResponseEntity> showProfile(Principal principal) {
    String email = principal.getName();
    return sellerService.showProfile(email);
  }

//---------------------------------------------------UPDATE------------------------------------------------------------

  /*
    URI to update profile
   */
  @PatchMapping(path = "/update-profile")
  public ResponseEntity<MessageResponseEntity> updateProfile(Principal principal,
      @Valid @RequestBody SellerProfileUpdate sellerProfileUpdate) {

    String email = principal.getName();
    return sellerService.updateProfile(email, sellerProfileUpdate);
  }

  /*
   URI to Update address by id
  */
  @PatchMapping(path = "/update-address/{id}")
  public ResponseEntity<MessageResponseEntity> updateAddress(Principal principal,
      @PathVariable(value = "id") Long addressId, @Valid @RequestBody AddressCO addressCO) {

    String email = principal.getName();
    return userService.updateAddress(email, addressId, addressCO);
  }

  /*
    URI to update the password of logged in user
   */
  @PatchMapping(path = "/update-password")
  public ResponseEntity<MessageResponseEntity> updatePassword(
      HttpServletRequest request, @Valid @RequestBody PasswordCO passwordCO) {

    String authHeader = request.getHeader("Authorization");
    String email = request.getRemoteUser();
    return userService.updatePassword(authHeader, passwordCO, email);

  }
}
