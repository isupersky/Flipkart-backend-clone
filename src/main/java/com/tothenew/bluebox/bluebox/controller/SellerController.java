package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.enitity.Seller;
import com.tothenew.bluebox.bluebox.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerController {

  @Autowired
  SellerService sellerService;

  @GetMapping(path = "/seller")
  public Iterable<Seller> getTestUser() {
    return sellerService.getTestUser();
  }


  @PostMapping(path = "/seller-register")
  public String register(@RequestBody Seller seller) {
    return sellerService.registerSeller(seller);
  }
}
