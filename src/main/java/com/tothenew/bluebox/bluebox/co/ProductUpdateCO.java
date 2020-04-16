package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.NotEmpty;

public class ProductUpdateCO {

  @NotEmpty(message = "name of the product can not be empty")
  private String name;
  private String description;
  private boolean isCancellable = false;
  private boolean isReturnable = false;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isCancellable() {
    return isCancellable;
  }

  public void setCancellable(boolean cancellable) {
    isCancellable = cancellable;
  }

  public boolean isReturnable() {
    return isReturnable;
  }

  public void setReturnable(boolean returnable) {
    isReturnable = returnable;
  }
}
