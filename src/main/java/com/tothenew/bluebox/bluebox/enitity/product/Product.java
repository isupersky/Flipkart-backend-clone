package com.tothenew.bluebox.bluebox.enitity.product;

import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Product {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name = "sellerUserId")
  private Seller sellerUserId;
  private String name;
  private String description;
  @ManyToOne
  @JoinColumn(name = "categoryId")
  private Category categoryId;
  private boolean isCancellable;
  private boolean isReturnable;
  private String brand;
  private boolean isActive;


  public Product() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Seller getSellerUserId() {
    return sellerUserId;
  }

  public void setSellerUserId(Seller sellerUserId) {
    this.sellerUserId = sellerUserId;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", sellerUserId=" + sellerUserId +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", categoryId=" + categoryId +
        ", isCancellable=" + isCancellable +
        ", isReturnable=" + isReturnable +
        ", brand='" + brand + '\'' +
        ", isActive=" + isActive +
        '}';
  }
}
