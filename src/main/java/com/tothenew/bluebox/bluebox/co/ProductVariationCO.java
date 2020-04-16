package com.tothenew.bluebox.bluebox.co;


import java.util.HashMap;
import javax.validation.constraints.NotNull;

public class ProductVariationCO {

  @NotNull(message = "Product Id is must")
  private Long productId;
  @NotNull(message = "quantity is must")
  private Integer quantityAvailable;
  @NotNull(message = "price is must")
  private Float price;
  @NotNull
  private HashMap metadataHashmap;

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getQuantityAvailable() {
    return quantityAvailable;
  }

  public void setQuantityAvailable(Integer quantityAvailable) {
    this.quantityAvailable = quantityAvailable;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public HashMap getMetadataHashmap() {
    return metadataHashmap;
  }

  public void setMetadataHashmap(HashMap metadataHashmap) {
    this.metadataHashmap = metadataHashmap;
  }
}
