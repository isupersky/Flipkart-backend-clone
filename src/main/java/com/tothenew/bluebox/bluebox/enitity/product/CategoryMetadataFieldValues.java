package com.tothenew.bluebox.bluebox.enitity.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(CategoryMetadataFieldValuesId.class)
public class CategoryMetadataFieldValues {

  @Id
  @ManyToOne
  @JoinColumn(name = "CategoryMetadataFieldId")
  private CategoryMetadataField categoryMetadataField;

  @Id
  @ManyToOne
  @JoinColumn(name = "CategoryId")
  private Category category;

  private String value;

  public CategoryMetadataFieldValues(
      CategoryMetadataField categoryMetadataField,
      Category category, String value) {
    this.categoryMetadataField = categoryMetadataField;
    this.category = category;
    this.value = value;
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

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "CategoryMetadataFieldValues{" +
        "categoryMetadataField=" + categoryMetadataField +
        ", category=" + category +
        ", value='" + value + '\'' +
        '}';
  }
}
