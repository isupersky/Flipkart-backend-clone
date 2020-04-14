package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.co.CategoryCO;
import com.tothenew.bluebox.bluebox.co.CategoryMetadataFieldCO;
import com.tothenew.bluebox.bluebox.co.CategoryUpdateCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.service.AdminService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {

  @Autowired
  AdminService adminService;

  /*
    URI to Add Category Metadata Field
   */
  @PostMapping("/admin/add-metadata-field")
  public ResponseEntity<MessageResponseEntity> addMetadataField(
      @Valid @RequestBody CategoryMetadataFieldCO categoryMetadataFieldCO) {
    return adminService.addMetadataField(categoryMetadataFieldCO);
  }

  //Admin Function to List All Category Metadata Field
  @GetMapping("/admin/metadata")
  public ResponseEntity<MessageResponseEntity> listAllMetadata(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    return adminService.listAllMetadata(pageNo, pageSize, sortBy);

  }

  /*
    URI to Add New Category
   */
  @PostMapping("/add-category")
  public ResponseEntity<Object> addCategory(@Valid @RequestBody CategoryCO categoryCO) {
    return adminService.addCategory(categoryCO);
  }

  @GetMapping("/category")
  public ResponseEntity<MessageResponseEntity> listAllCategory(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    return adminService.listAllCategory(pageNo, pageSize, sortBy);
  }

  /*
    URI to List One Category details.
   */
  @GetMapping("/category/{id}")
  public ResponseEntity<MessageResponseEntity> listOneCategory(@PathVariable Long id) {
    return adminService.getCategoryDetails(id);
  }

  /*
    URI to Update One Category
   */
  @PutMapping("/update-category")
  public ResponseEntity<MessageResponseEntity> updateCategory(
      @Valid @RequestBody CategoryUpdateCO categoryUpdateCO) {
    return adminService.updateCategory(categoryUpdateCO);
  }
}