package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminProductController {

  @Autowired
  ProductService productService;


  /*
    URI to activate a product
   */
  @PutMapping("/product/activate/{id}")
  public ResponseEntity<MessageResponseEntity> activateProduct(@PathVariable Long id) {
    return productService.activateProduct(id);
  }

  /*
    URI to deactivate a product
   */
  @PutMapping("/product/deactivate/{id}")
  public ResponseEntity<MessageResponseEntity> deActivateProduct(@PathVariable Long id) {
    return productService.activateProduct(id);
  }

  /* throwing error
     URI to fetch Details Of a Product given its id
    */
  @GetMapping("/product/{id}")
  public ResponseEntity<MessageResponseEntity> getProductById(@PathVariable Long id) {

    return productService.getProductDetailsById(id);
  }

}
