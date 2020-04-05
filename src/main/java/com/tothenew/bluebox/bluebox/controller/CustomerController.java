package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.dto.CustomerDto;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  @Autowired
  CustomerService customerService;

//  @PostMapping(path = "/customer-register")
//  public String registerCustomer(@RequestBody Customer customer) {
//    customerService.createCustomer(customer);
//    return "Success".toUpperCase();
//  }

  @PostMapping(path = "/customer-register")
  public Object registerCustomer(@Valid @RequestBody CustomerDto customerDto) {
    return customerService.registerCustomer(customerDto);

  }

  @RequestMapping(path = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
  public String confirmUserAccount(@RequestParam("token") String confirmationToken) {

    return customerService.validateCustomer(confirmationToken);
  }



}
