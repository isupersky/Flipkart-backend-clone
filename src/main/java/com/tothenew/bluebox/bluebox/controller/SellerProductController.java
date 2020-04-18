package com.tothenew.bluebox.bluebox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tothenew.bluebox.bluebox.co.ProductCO;
import com.tothenew.bluebox.bluebox.co.ProductUpdateCO;
import com.tothenew.bluebox.bluebox.co.ProductVariationCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.exception.ProductNotFoundException;
import com.tothenew.bluebox.bluebox.service.ProductService;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    URI to fetch Details Of a Product given its id
   */
  @GetMapping("/product/{id}")
  public ResponseEntity<Object> getProductById(Principal principal, @PathVariable Long id) {
    String email = principal.getName();
    return productService.getProductByIdSeller(email, id);
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
  @PutMapping("/product/update/{id}")
  public ResponseEntity<Object> updateOneProduct(Principal principal,
      @PathVariable(value = "id") Long productId, @Valid @RequestBody
      ProductUpdateCO productUpdateCO) {
    String email = principal.getName();
    return productService.updateOneProduct(email, productId, productUpdateCO);
  }


  /*
   URI to add variation of a product
  */
  @PostMapping(path = "/add-product-variation")
  public ResponseEntity<MessageResponseEntity> addProductVariation(
      @Valid @RequestBody ProductVariationCO productVariationCO) {

    try {
      return productService.addProductVariation(productVariationCO);
    } catch (ProductNotFoundException pe) {
      throw pe;
    } catch (JsonProcessingException jpe) {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.BAD_REQUEST, "Something went wrong".toUpperCase())
          , HttpStatus.BAD_REQUEST);
    }

  }

  /*
    URI to add variation of a product
   */
  @PostMapping(path = "/add-product-variation-images/{id}")
  public ResponseEntity<MessageResponseEntity> addProductVariationImages(
      @PathVariable(value = "id") Long id, List<MultipartFile> imageFiles) {

    try {
      return productService.addProductVariationImages(id, imageFiles);
    } catch (ProductNotFoundException pe) {
      throw pe;
    } catch (IOException ioe) {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.BAD_REQUEST, "Something went wrong".toUpperCase())
          , HttpStatus.BAD_REQUEST);
    }

  }


  /* image list not coming up
    URI to fetch details of one product Varion given its Id
   */
  @GetMapping("/product-variation/{id}")
  public ResponseEntity<MessageResponseEntity> getProductVariationById(Principal principal,
      @PathVariable Long id)
      throws IOException {
    String email = principal.getName();
    return productService.getProductVariationById(email, id);
  }


  /*throws error 500
    URI to Fetch Details all Products variation
   */
  @GetMapping("/product-variations")
  public ResponseEntity<MessageResponseEntity> listAllProductVariation(Principal principal,
      @PathVariable(value = "id") Long id,
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {
    String email = principal.getName();
    return productService.listAllProductVariation(email, id, pageNo, pageSize, sortBy);
  }

  /*
   Update One Product Variation
  */
//  @PutMapping("/product/update/{id}")
//  public ResponseEntity<Object> updateOneProductvariation(Principal principal,@PathVariable(value = "id")Long productId ,@Valid @RequestBody
//      ProductUpdateCO productUpdateCO) {
//    String email = principal.getName();
//    return productService.updateOneProduct(email,productId, productUpdateCO);
//  }

}
