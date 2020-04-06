package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import com.tothenew.bluebox.bluebox.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/sellers")
public class SellerController {

  @Autowired
  SellerService sellerService;


  @PostMapping(path = "/register")
  public String register(@RequestBody Seller seller) {
    return sellerService.registerSeller(seller);
  }
}
