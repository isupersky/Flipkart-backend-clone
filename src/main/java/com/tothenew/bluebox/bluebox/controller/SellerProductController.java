package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.co.ProductCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.ProductService;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerProductController {

  @Autowired
  ProductService productService;

  @PostMapping(path = "/add-Product")
  public ResponseEntity<MessageResponseEntity> addProduct(Principal principal,
      @Valid @RequestBody ProductCO productCO) {
    String email = principal.getName();
    return productService.addProduct(email, productCO);
  }
}
