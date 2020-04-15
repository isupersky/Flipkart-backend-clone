package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerCategoryController {

  @Autowired
  SellerService sellerService;

  /*
    URI to list all category and related metadata fields
   */
  @GetMapping("/category")
  public ResponseEntity<MessageResponseEntity> listAllCategory(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    return sellerService.listAllCategory(pageNo, pageSize, sortBy);
  }
}
