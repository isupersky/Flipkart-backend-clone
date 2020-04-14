package com.tothenew.bluebox.bluebox.enitity.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Category {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  @ManyToOne
  @JoinColumn(name = "parentId")
  private Category parentId;
  private boolean isLeafNode = false;

  public Category(Long id, String name,
      Category parentId, boolean isLeafNode) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
    this.isLeafNode = isLeafNode;
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
    return isLeafNode;
  }

  public void setLeafNode(boolean leafNode) {
    this.isLeafNode = leafNode;
  }

  @Override
  public String toString() {
    return "Category{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", parentId=" + parentId +
        ", leafNode=" + isLeafNode +
        '}';
  }
}
