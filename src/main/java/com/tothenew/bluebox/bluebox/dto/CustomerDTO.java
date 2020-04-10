package com.tothenew.bluebox.bluebox.dto;

public class CustomerDTO extends UserDTO {

  private Long contact;

  public CustomerDTO() {
  }

  public Long getContact() {
    return contact;
  }

  public void setContact(Long contact) {
    this.contact = contact;
  }

  @Override
  public String toString() {
    return "CustomerDTO{" +
        "contact=" + contact +
        '}';
  }
}
