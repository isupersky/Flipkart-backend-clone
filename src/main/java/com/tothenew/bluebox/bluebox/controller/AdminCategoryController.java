package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.co.CategoryCO;
import com.tothenew.bluebox.bluebox.co.CategoryMetadataFieldCO;
import com.tothenew.bluebox.bluebox.co.CategoryMetadataFieldValueCO;
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


//---------------------------------------------COMPLETE---------------------------------------------
@RestController
@RequestMapping("/admin")
public class AdminCategoryController {

  @Autowired
  AdminService adminService;

  /*
    URI to Add Category Metadata Field
   */
  @PostMapping("/add-metadata-field")
  public ResponseEntity<MessageResponseEntity> addMetadataField(
      @Valid @RequestBody CategoryMetadataFieldCO categoryMetadataFieldCO) {
    return adminService.addMetadataField(categoryMetadataFieldCO);
  }

  /*
    URI to List All Category Metadata Field
   */
  @GetMapping("/metadata")
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

  @GetMapping("/categories")
  public ResponseEntity<MessageResponseEntity> listAllCategory(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    return adminService.listAllCategory(pageNo, pageSize, sortBy);
  }

  /*
    URI to List One Category details.
   */
  @GetMapping("/categories/{id}")
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

  /*
    URI to add new Category Metadata Field values
   */
  @PostMapping("/add-category-metadata-field-value")
  public ResponseEntity<MessageResponseEntity> addCategoryMetadataFieldValues(@Valid @RequestBody
      CategoryMetadataFieldValueCO categoryMetadataFieldValueCO) {
    return adminService.addCategoryMetadataFieldValues(categoryMetadataFieldValueCO);
  }

  /*
   URI to add new Category Metadata Field values
  */
  @PutMapping("/update-category-metadata-field-value")
  public ResponseEntity<MessageResponseEntity> updateCategoryMetadataFieldValues(@Valid @RequestBody
      CategoryMetadataFieldValueCO categoryMetadataFieldValueCO) {
    return adminService.updateCategoryMetadataFieldvalues(categoryMetadataFieldValueCO);
  }
}
