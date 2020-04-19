package com.tothenew.bluebox.bluebox.dto;

import com.tothenew.bluebox.bluebox.enitity.product.Category;

public class ProductDTO {

  private Long id;
  private String name;
  private String description;
  private Category categoryDetail;
  private boolean isCancellable = false;
  private boolean isReturnable = false;
  private String brand;
  private boolean isActive;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Category getCategoryDetail() {
    return categoryDetail;
  }

  public void setCategoryDetail(Category categoryDetail) {
    this.categoryDetail = categoryDetail;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
