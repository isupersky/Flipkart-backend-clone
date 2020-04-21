package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminProductController {

  @Autowired
  private ProductService productService;


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
    return productService.deActivateProduct(id);
  }

  /* throwing error
     URI to fetch Details Of a Product given its id
    */
  @GetMapping("/product/{id}")
  public ResponseEntity<MessageResponseEntity> getProductById(@PathVariable(value = "id") Long id) {

    return productService.getProductDetailsById(id);
  }

  @GetMapping("/products/{catId}")
  public ResponseEntity<MessageResponseEntity> listAllProducts(
      @PathVariable(value = "catId") Long catId, @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    return productService.getAllProductByCategoryId(pageNo, pageSize, sortBy, catId);
  }

}
