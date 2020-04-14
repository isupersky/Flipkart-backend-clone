package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoryMetadataFieldValueCO {

  @NotNull
  private Long categoryId;
  @NotNull
  private Long categoryMetadataFieldId;
  @NotEmpty(message = "value Not entered")
  private String value;

  public CategoryMetadataFieldValueCO(@NotNull Long categoryId,
      @NotNull Long categoryMetadataFieldId,
      @NotEmpty(message = "value Not entered") String value) {
    this.categoryId = categoryId;
    this.categoryMetadataFieldId = categoryMetadataFieldId;
    this.value = value;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public Long getCategoryMetadataFieldId() {
    return categoryMetadataFieldId;
  }

  public void setCategoryMetadataFieldId(Long categoryMetadataFieldId) {
    this.categoryMetadataFieldId = categoryMetadataFieldId;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "CategoryMetadataFieldValueCO{" +
        "categoryId=" + categoryId +
        ", categoryMetadataFieldId=" + categoryMetadataFieldId +
        ", value='" + value + '\'' +
        '}';
  }
}
