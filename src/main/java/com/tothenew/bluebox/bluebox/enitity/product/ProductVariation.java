package com.tothenew.bluebox.bluebox.enitity.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class ProductVariation {

//----------------------------------------COMPLETE-------------------------------------

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name = "productId")
  private Product productId;
  private Integer quantityAvailable;
  private Float price;
  private String metadata;
  private String images;
  @Transient
  private HashMap metadataHashmap;

  @Transient
  private HashSet primaryImageName;

  private boolean isActive;

  public ProductVariation() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product getProductId() {
    return productId;
  }

  public void setProductId(Product productId) {
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

  public HashSet getPrimaryImageName() {
    return primaryImageName;
  }

  public void setPrimaryImageName(HashSet primaryImageName) {
    this.primaryImageName = primaryImageName;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  public HashMap<String, String> getMetadataHashmap() {
    return metadataHashmap;
  }

  public void setMetadataHashmap(HashMap<String, String> metadataHashmap) {
    this.metadataHashmap = metadataHashmap;
  }

  public void jsonMetadataStringSerialize() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    this.metadata = objectMapper.writeValueAsString(metadataHashmap);
  }

  public void jsonMetadataStringDeserialize() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    metadataHashmap = objectMapper.readValue(this.metadata, HashMap.class);
  }

  public void imageStringSerialize() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    this.images = objectMapper.writeValueAsString(primaryImageName);
  }

  public void imageStringDeserialize() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    primaryImageName = objectMapper.readValue(this.images, HashSet.class);
  }

  @Override
  public String toString() {
    return "ProductVariation{" +
        "id=" + id +
        ", productId=" + productId +
        ", quantityAvailable=" + quantityAvailable +
        ", price=" + price +
        ", metadata='" + metadata + '\'' +
        ", metadataHashmap=" + metadataHashmap +
        ", isActive=" + isActive +
        '}';
  }

}
