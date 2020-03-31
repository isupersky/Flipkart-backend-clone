package com.tothenew.bluebox.bluebox.enitity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
public class Customer extends User {

  @Pattern(regexp = "^(?:\\s+|)((0|(?:(\\+|)91))(?:\\s|-)*(?:(?:\\d(?:\\s|-)*\\d{9})|(?:\\d{2}(?:\\s|-)*\\d{8})|(?:\\d{3}(?:\\s|-)*\\d{7}))|\\d{10})(?:\\s+|)$"
      , message = "The Contact No. is not valid")
  @NotEmpty(message = "Contact No is a mandatory field")
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
