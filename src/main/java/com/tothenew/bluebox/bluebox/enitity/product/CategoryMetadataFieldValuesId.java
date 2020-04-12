package com.tothenew.bluebox.bluebox.enitity.product;

import java.io.Serializable;

public class CategoryMetadataFieldValuesId implements Serializable {

  private CategoryMetadataField categoryMetadataField;

  private Category category;

  public CategoryMetadataFieldValuesId(
      CategoryMetadataField categoryMetadataField,
      Category category) {
    this.categoryMetadataField = categoryMetadataField;
    this.category = category;
  }

  public CategoryMetadataField getCategoryMetadataField() {
    return categoryMetadataField;
  }

  public void setCategoryMetadataField(
      CategoryMetadataField categoryMetadataField) {
    this.categoryMetadataField = categoryMetadataField;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
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
