package com.tothenew.bluebox.bluebox.dto;


import java.util.HashMap;
import java.util.HashSet;

public class ProductVariationDTO {

  private ProductDTO productId;
  private Integer quantityAvailable;
  private Float price;
  private HashMap metadataHashmap;
  private boolean isActive;
  private HashSet<String> primaryImageName;

  public ProductDTO getProductId() {
    return productId;
  }

  public void setProductId(ProductDTO productId) {
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public HashSet<String> getPrimaryImageName() {
    return primaryImageName;
  }

  public void setPrimaryImageName(HashSet<String> primaryImageName) {
    this.primaryImageName = primaryImageName;
  }
}
