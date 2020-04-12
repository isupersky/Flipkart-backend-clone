package com.tothenew.bluebox.bluebox.enitity.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Category {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  @ManyToOne
  private Category parentId;
  private boolean leafNode = false;

  public Category(Long id, String name,
      Category parentId, boolean leafNode) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
    this.leafNode = leafNode;
  }

  public Category() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category getParentId() {
    return parentId;
  }

  public void setParentId(Category parentId) {
    this.parentId = parentId;
  }

  public boolean isLeafNode() {
    return leafNode;
  }

  public void setLeafNode(boolean leafNode) {
    this.leafNode = leafNode;
  }

  @Override
  public String toString() {
    return "Category{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", parentId=" + parentId +
        ", leafNode=" + leafNode +
        '}';
  }
}
