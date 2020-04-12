package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.co.AddressCO;
import com.tothenew.bluebox.bluebox.co.CustomerCO;
import com.tothenew.bluebox.bluebox.co.CustomerProfileUpdateCO;
import com.tothenew.bluebox.bluebox.co.EmailCO;
import com.tothenew.bluebox.bluebox.co.PasswordCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import com.tothenew.bluebox.bluebox.service.UserService;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  CustomerService customerService;

  @Autowired
  UserService userService;

//---------------------------------------------------CREATE------------------------------------------------------------

  /*
    URI for registration of a customer
   */
  @PostMapping(path = "/register")
  public ResponseEntity<MessageResponseEntity> registerCustomer(
      @Valid @RequestBody CustomerCO customerCO) {
    return customerService.registerCustomer(customerCO);
  }

  /*
    URI for confirming Customer email id.
   */
  @RequestMapping(path = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseEntity<MessageResponseEntity> confirmUserAccount(
      @RequestParam("token") String confirmationToken) {
    return customerService.activateCustomer(confirmationToken);
  }

  /*
    URI to resend Activation Token
   */
  @PostMapping(path = "/resend-activation")
  public ResponseEntity<MessageResponseEntity> resendActivationToken(
      @Valid @RequestBody EmailCO emailCO) {
    return customerService.resendActivationToken(emailCO.getEmail());
  }

  /*
    URI to add new Address
   */
  @PostMapping(path = "/new-address")
  public ResponseEntity<MessageResponseEntity> addAddress(Principal principal,
      @Valid @RequestBody AddressCO addressCO) {
    String email = principal.getName();
    return customerService.addAddress(email, addressCO);
  }

//---------------------------------------------------READ------------------------------------------------------------

  /*
    URI to access profile details
   */
  @GetMapping(path = "/profile")
  public ResponseEntity<MessageResponseEntity> showProfile(Principal principal) {
    String email = principal.getName();
    return customerService.showProfile(email);
  }

  /*
    URI to access Address List
   */
  @GetMapping(path = "/addresses")
  public ResponseEntity<MessageResponseEntity> showAddress(Principal principal) {
    String email = principal.getName();
    return customerService.showAddresses(email);
  }

//---------------------------------------------------UPDATE------------------------------------------------------------

  /*
    URI to update the password of logged in user
   */
  @PatchMapping(path = "/update-password")
  public ResponseEntity<MessageResponseEntity> updatePassword(HttpServletRequest request,
      @Valid @RequestBody PasswordCO passwordCO) {

    String authHeader = request.getHeader("Authorization");
    String email = request.getRemoteUser();
    return userService.updatePassword(authHeader, passwordCO, email);

  }

  /*
    URI to update profile
   */
  @PatchMapping(path = "/update-profile")
  public ResponseEntity<MessageResponseEntity> updateProfile(Principal principal,
      @Valid @RequestBody CustomerProfileUpdateCO customerProfileUpdateCO) {

    String email = principal.getName();
    return customerService.updateProfile(email, customerProfileUpdateCO);
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

//---------------------------------------------------DELETE------------------------------------------------------------

  /*
    URI to delete an address by given address Id
   */
  @DeleteMapping(path = "/delete-address/{id}")
  public ResponseEntity<MessageResponseEntity> deleteAddress(Principal principal,
      @PathVariable(value = "id") Long addressId) {
    String email = principal.getName();
    return customerService.deleteAddress(email, addressId);

  }
}
