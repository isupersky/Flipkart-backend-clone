package com.tothenew.bluebox.bluebox.co;

import java.util.HashMap;
import javax.validation.constraints.NotNull;

public class ProductVariationUpdateCO {

  @NotNull(message = "quantity is must")
  private Integer quantityAvailable;
  @NotNull(message = "price is must")
  private Float price;
  @NotNull
  private HashMap metadataHashmap;

  private boolean isActive;

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
}
