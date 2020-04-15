package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerCategoryController {

  @Autowired
  CustomerService customerService;

  /*
     Return List all root level categories if no ID is passed,
      else list of all immediate child nodes of passed category ID
   */
  @GetMapping({"/categories/{id}", "/categories"})
  public ResponseEntity<MessageResponseEntity> listAllCustomerCategories(
      @PathVariable(name = "id", required = false) Long id) {
    return customerService.listAllCustomerCategories(id);
  }
}
