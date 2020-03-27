package com.tothenew.bluebox.bluebox.enitity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
public class Customer extends User {


  private Long contact;

  //	Default Constructors
  public Customer() {
  }

  public Long getContact() {
    return contact;
  }

  public void setContact(Long contact) {
    this.contact = contact;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "contact=" + contact +
        '}';
  }
}
