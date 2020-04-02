package com.tothenew.bluebox.bluebox.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

  @GetMapping(path = "/admin")
  public String adminTest() {
    return "Hello Admin";
  }

  @GetMapping(path = "/")
  public String test() {
    return "Hello World ";
  }
}
