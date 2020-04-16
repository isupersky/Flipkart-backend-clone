package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductCO {

  @NotEmpty(message = "Please enter the name of the product")
  private String name;
  private String description = "No description".toUpperCase();
  @NotNull(message = "category id not entered")
  private Long categoryId;
  private boolean isCancellable = false;
  private boolean isReturnable = false;
  @NotEmpty(message = "Brand name can not be empty")
  private String brand;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public boolean isCancellable() {
    return isCancellable;
  }

  public void setCancellable(boolean cancellable) {
    isCancellable = cancellable;
  }

  public boolean isReturnable() {
    return isReturnable;
  }

  public void setReturnable(boolean returnable) {
    isReturnable = returnable;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  @Override
  public String toString() {
    return "ProductCO{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", categoryId=" + categoryId +
        ", isCancellable=" + isCancellable +
        ", isReturnable=" + isReturnable +
        ", brand='" + brand + '\'' +
        '}';
  }
}
