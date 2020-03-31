package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.enitity.Customer;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping(path = "/customer")
  public Iterable<Customer> getTestUser() {
    return customerService.getTestUser();
  }

  @PostMapping(value = "/customer-register")
  public Object registerCustomer(@Valid @RequestBody Customer customer) {

    return customerService.registerCustomer(customer);

  }

  @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
  public String confirmUserAccount(@RequestParam("token") String confirmationToken) {

    return customerService.validateCustomer(confirmationToken);
  }

}
