package com.tothenew.bluebox.bluebox.co;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class CategoryCO {

  @NotNull(message = "Please provide Name")
  @NotBlank(message = "Please provide valid Name")
  private String name;
  private Long parentId;

  public String getName() {
    return name.toUpperCase();
  }

  public void setName(String name) {
    this.name = name.toUpperCase();
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }
}
