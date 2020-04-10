package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.co.AddressCO;
import com.tothenew.bluebox.bluebox.co.CustomerCO;
import com.tothenew.bluebox.bluebox.co.EmailCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping(path = "/profile")
  public ResponseEntity<MessageResponseEntity> showProfile(Principal principal) {
    String email = principal.getName();
    return customerService.showProfile(email);
  }

  @GetMapping(path = "/addresses")
  public ResponseEntity<MessageResponseEntity> showAddress(Principal principal) {
    String email = principal.getName();
    return customerService.showAddresses(email);
  }

}
