package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.enitity.Customer;
import com.tothenew.bluebox.bluebox.enitity.User;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  @Autowired
  CustomerService customerService;

  @PostMapping(path = "/customer-register")
  public String registerCustomer(@RequestBody Customer customer) {
    customerService.createCustomer(customer);
    return "Success".toUpperCase();
  }

  @GetMapping(path = "/customer")
  public Iterable<User> getTestUser() {
    return customerService.getTestUser();
  }

}
