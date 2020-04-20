package com.tothenew.bluebox.bluebox.dto;


import java.util.HashMap;

public class ProductVariationDTO {

  private Long id;
  private ProductDTO productId;
  private Integer quantityAvailable;
  private Float price;
  private HashMap metadataHashmap;
  private boolean isActive;
  private String primaryImageName;
  private String secondaryImageName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public String getPrimaryImageName() {
    return primaryImageName;
  }

  public void setPrimaryImageName(String primaryImageName) {
    this.primaryImageName = primaryImageName;
  }

  public String getSecondaryImageName() {
    return secondaryImageName;
  }

  public void setSecondaryImageName(String secondaryImageName) {
    this.secondaryImageName = secondaryImageName;
  }
}
