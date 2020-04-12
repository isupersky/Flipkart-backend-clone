package com.tothenew.bluebox.bluebox.enitity.order;

import com.tothenew.bluebox.bluebox.enitity.product.ProductVariation;
import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

//----------------------------------------COMPLETE-------------------------------------
@Entity
@IdClass(CartId.class)
public class Cart {

  @Id
  @OneToOne
  @JoinColumn(name = "customerUserId")
  private Customer customerUserId;
  private Integer quantity;
  private boolean isWishListItem;

  @Id
  @ManyToOne
  @JoinColumn(name = "productVariationid")
  private ProductVariation productVariationid;

  public Cart() {
  }

  public Customer getCustomerUserId() {
    return customerUserId;
  }

  public void setCustomerUserId(Customer customerUserId) {
    this.customerUserId = customerUserId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public boolean isWishListItem() {
    return isWishListItem;
  }

  public void setWishListItem(boolean wishListItem) {
    isWishListItem = wishListItem;
  }

  public ProductVariation getProductVariationid() {
    return productVariationid;
  }

  public void setProductVariationid(
      ProductVariation productVariationid) {
    this.productVariationid = productVariationid;
  }

  @Override
  public String toString() {
    return "Cart{" +
        "customerUserId=" + customerUserId +
        ", quantity=" + quantity +
        ", isWishListItem=" + isWishListItem +
        ", productVariationid=" + productVariationid +
        '}';
  }
}
