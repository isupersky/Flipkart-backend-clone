package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.NotNull;

public class CustomerProfileUpdateCO extends UserProfileUpdate {

  @NotNull(message = "Contact No is a mandatory field")
  private Long contact;

  public CustomerProfileUpdateCO() {
  }

  public Long getContact() {
    return contact;
  }

  public void setContact(Long contact) {
    this.contact = contact;
  }

  @Override
  public String toString() {
    return "CustomerProfileUpdateCO{" +
        "contact=" + contact +
        '}';
  }
}
