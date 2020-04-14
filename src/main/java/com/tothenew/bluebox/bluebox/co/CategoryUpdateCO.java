package com.tothenew.bluebox.bluebox.co;

public class CategoryUpdateCO {

  private Long id;
  private String name;

  public CategoryUpdateCO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name.toUpperCase();
  }

  public void setName(String name) {
    this.name = name.toUpperCase();
  }
}
