package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerProductController {

  @Autowired
  private ProductService productService;

  /*
    URI to fetch Details Of a Product given its id
   */
  @GetMapping("/product/{id}")
  public ResponseEntity<MessageResponseEntity> getProductById(@PathVariable Long id) {

    return productService.getProductDetailsById(id);
  }


  @GetMapping("/products/{catId}")
  public ResponseEntity<MessageResponseEntity> listAllProducts(
      @PathVariable(value = "catId") Long catId, @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    return productService.getAllProductByCategoryId(pageNo, pageSize, sortBy, catId);
  }

  /*
    URI to fetch Similar Product given its id
   */
  @GetMapping(value = "/view-similiar-products")
  public ResponseEntity<MessageResponseEntity> viewSimiliarProducts(
      @RequestParam Long productId,
      @RequestParam(defaultValue = "10") String page,
      @RequestParam(defaultValue = "0") String pageoff,
      @RequestParam(defaultValue = "id") String sortby,
      @RequestParam(defaultValue = "ASC") String order) {
    Integer pagesize = Integer.parseInt(page);
    Integer pageoffset = Integer.parseInt(pageoff);

    return productService
        .viewSimiliarProductsCustomer(productId, pageoffset, pagesize, sortby, order);

  }
}
