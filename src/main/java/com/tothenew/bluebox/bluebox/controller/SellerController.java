package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.co.SellerCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.SellerService;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/seller")
public class SellerController {

  @Autowired
  SellerService sellerService;

//---------------------------------------------------CREATE------------------------------------------------------------

  @PostMapping(path = "/register")
  public ResponseEntity<MessageResponseEntity> register(@Valid @RequestBody SellerCO sellerCO) {
    return sellerService.registerSeller(sellerCO);
  }

//---------------------------------------------------READ------------------------------------------------------------

  @GetMapping(path = "/profile")
  public ResponseEntity<MessageResponseEntity> showProfile(Principal principal) {
    String email = principal.getName();
    return sellerService.showProfile(email);
  }

}
