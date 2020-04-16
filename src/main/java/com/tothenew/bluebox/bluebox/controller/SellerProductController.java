package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.co.ProductCO;
import com.tothenew.bluebox.bluebox.co.ProductVariationCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.exception.ProductNotFoundException;
import com.tothenew.bluebox.bluebox.service.ProductService;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerProductController {

  @Autowired
  ProductService productService;

  /*
    URI to add new Product.
   */
  @PostMapping(path = "/add-product")
  public ResponseEntity<MessageResponseEntity> addProduct(Principal principal,
      @Valid @RequestBody ProductCO productCO) {
    String email = principal.getName();
    return productService.addProduct(email, productCO);
  }

  /*
    URI to add variation of a product
   */
  @PostMapping(path = "/add-product-variation")
  public ResponseEntity<MessageResponseEntity> addProductVariation(
      @Valid @RequestBody ProductVariationCO productVariationCO) {

    try {
      return productService.addProductvariation(productVariationCO);
    } catch (ProductNotFoundException pe) {
      throw pe;
    } catch (Exception e) {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.BAD_REQUEST, "Something went wrong")
          , HttpStatus.BAD_REQUEST);
    }

  }

  /*
    URI to fetch Details Of a Product given its id
   */
  @GetMapping("/product/{id}")
  public ResponseEntity<Object> listOneProduct(Principal principal, @PathVariable Long id) {
    String email = principal.getName();
    return productService.listOneProduct(email, id);
  }

  /*
    URI to Fetch Details all Products
   */
  @GetMapping("/products")
  public ResponseEntity<Object> listAllProduct(Principal principal,
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {
    String email = principal.getName();
    return productService.listAllProduct(email, pageNo, pageSize, sortBy);
  }

  /*
    URI to Delete a Product
   */
  @DeleteMapping("/product/delete/{id}")
  public ResponseEntity<Object> deleteOneProduct(Principal principal, @PathVariable Long id) {
    String email = principal.getName();
    return productService.deleteProduct(email, id);
  }

  /*
    Update One Product
   */

}
