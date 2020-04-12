package com.tothenew.bluebox.bluebox.enitity.product;

import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import java.io.Serializable;

public class ProductReviewId implements Serializable {

  private Customer customerUserId;

  private Product productId;

  public ProductReviewId(Customer customerUserId,
      Product productId) {
    this.customerUserId = customerUserId;
    this.productId = productId;
  }

  public Customer getCustomerUserId() {
    return customerUserId;
  }

  public void setCustomerUserId(Customer customerUserId) {
    this.customerUserId = customerUserId;
  }

  public Product getProductId() {
    return productId;
  }

  public void setProductId(Product productId) {
    this.productId = productId;
  }

  @Override
  public String toString() {
    return "ProductReviewId{" +
        "customerUserId=" + customerUserId +
        ", productId=" + productId +
        '}';
  }
}

