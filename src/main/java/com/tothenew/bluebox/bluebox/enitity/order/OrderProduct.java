package com.tothenew.bluebox.bluebox.enitity.order;

import com.tothenew.bluebox.bluebox.enitity.product.ProductVariation;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

//----------------------------------------INCOMPLETE-------------------------------------

@Entity
public class OrderProduct {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name = "orderId")
  private Orders orderid;
  private Integer quantity;
  @OneToOne
  @JoinColumn(name = "productVariationId")
  private ProductVariation productVariationId;
//  private Metadata producVariationMetadata;

  public OrderProduct() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Orders getOrderid() {
    return orderid;
  }

  public void setOrderid(Orders orderid) {
    this.orderid = orderid;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public ProductVariation getProductVariationId() {
    return productVariationId;
  }

  public void setProductVariationId(
      ProductVariation productVariationId) {
    this.productVariationId = productVariationId;
  }

  @Override
  public String toString() {
    return "OrderProduct{" +
        "id=" + id +
        ", orderid=" + orderid +
        ", quantity=" + quantity +
        ", productVariationId=" + productVariationId +
        '}';
  }
}
