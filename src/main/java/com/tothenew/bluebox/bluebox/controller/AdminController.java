package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  CustomerService customerService;

  @GetMapping(path = "/")
  public String adminTest() {
    return "Hello Admin";
  }

  @GetMapping(path = "/test-hello")
  public String test() {
    return "Hello World ";
  }

  @GetMapping(path = "/customers")
  public Iterable<Customer> getAllCustomers() {
    return customerService.getTestUser();
  }
}
