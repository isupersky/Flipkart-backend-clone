package com.tothenew.bluebox.bluebox.enitity.order;

import com.tothenew.bluebox.bluebox.enitity.product.ProductVariation;
import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import java.io.Serializable;

public class CartId implements Serializable {

  private Customer customerUserId;
  private ProductVariation productVariationid;

  public CartId(Customer customerUserId,
      ProductVariation productVariationid) {
    this.customerUserId = customerUserId;
    this.productVariationid = productVariationid;
  }

  public Customer getCustomerUserId() {
    return customerUserId;
  }

  public void setCustomerUserId(Customer customerUserId) {
    this.customerUserId = customerUserId;
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
    return "CartId{" +
        "customerUserId=" + customerUserId +
        ", productVariationid=" + productVariationid +
        '}';
  }
}
