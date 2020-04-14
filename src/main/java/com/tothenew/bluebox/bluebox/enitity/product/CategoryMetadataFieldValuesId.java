package com.tothenew.bluebox.bluebox.enitity.product;

import java.io.Serializable;

public class CategoryMetadataFieldValuesId implements Serializable {

  private static final long serialVersionUID = 1l;

  private Long categoryMetadataField;

  private Long category;

  public CategoryMetadataFieldValuesId() {
  }

  public CategoryMetadataFieldValuesId(Long categoryMetadataField, Long category) {
    this.categoryMetadataField = categoryMetadataField;
    this.category = category;
  }

  public Long getCategoryMetadataField() {
    return categoryMetadataField;
  }

  public void setCategoryMetadataField(Long categoryMetadataField) {
    this.categoryMetadataField = categoryMetadataField;
  }

  public Long getCategory() {
    return category;
  }

  public void setCategory(Long category) {
    this.category = category;
  }

  @Override
  public String toString() {
    return "CategoryMetadataFieldValuesId{" +
        "categoryMetadataField=" + categoryMetadataField +
        ", category=" + category +
        '}';
  }
}
