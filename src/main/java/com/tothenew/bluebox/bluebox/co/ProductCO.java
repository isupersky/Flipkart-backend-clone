package com.tothenew.bluebox.bluebox.co;

import com.tothenew.bluebox.bluebox.enitity.product.Category;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductCO {

  @NotEmpty
  private String name;
  private String description = "No description".toUpperCase();
  @NotBlank
  @NotNull
  private Category categoryId;
  private boolean isCancellable = false;
  private boolean isReturnable = false;
  @NotEmpty
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

  public Category getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Category categoryId) {
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
