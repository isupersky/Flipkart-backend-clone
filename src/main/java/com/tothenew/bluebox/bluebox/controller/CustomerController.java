package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.dto.CustomerDto;
import com.tothenew.bluebox.bluebox.dto.EmailDto;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
  public Object registerCustomer(@Valid @RequestBody CustomerDto customerDto) {
    return customerService.registerCustomer(customerDto);
  }

  /*
    URI for confirming Customer email id.
   */
  @RequestMapping(path = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseEntity<Object> confirmUserAccount(
      @RequestParam("token") String confirmationToken) {
    return customerService.activateCustomer(confirmationToken);
  }


  @PostMapping(path = "/resendactivation")
  public ResponseEntity<Object> resendActivationToken(@Valid @RequestBody EmailDto emailDto) {
    return customerService.resendActivationToken(emailDto.getEmail());
  }

//---------------------------------------------------READ------------------------------------------------------------

//  @GetMapping(path = "/profile")
//  public CustomerDto showProfile(Principal principal){
//    String email = principal.getName();
//    return customerService.principal.getName()
//  }
}
