package com.tothenew.bluebox.bluebox.enitity.product;

import com.tothenew.bluebox.bluebox.enitity.Customer;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class ProductReview {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name = "customerUserId")
  private Customer customerUserId;
  @ManyToOne
  @JoinColumn(name = "productId")
  private Product productId;
  private String review;

  @Min(value = 0, message = "The minimum rating is 0")
  @Max(value = 5, message = "The maximum rating is 5")
  private Integer rating;

  public ProductReview() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  @Override
  public String toString() {
    return "ProductReview{" +
        "id=" + id +
        ", customerUserId=" + customerUserId +
        ", productId=" + productId +
        ", review='" + review + '\'' +
        ", rating=" + rating +
        '}';
  }
}
