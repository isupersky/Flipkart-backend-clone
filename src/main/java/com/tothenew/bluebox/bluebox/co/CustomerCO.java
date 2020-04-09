package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.NotNull;

public class CustomerCO extends UserCO {

  //  @Pattern(regexp = "^(?:\\s+|)((0|(?:(\\+|)91))(?:\\s|-)*(?:(?:\\d(?:\\s|-)*\\d{9})|(?:\\d{2}(?:\\s|-)*\\d{8})|(?:\\d{3}(?:\\s|-)*\\d{7}))|\\d{10})(?:\\s+|)$", message = "The Contact No. is not valid")
//  @Size(min = 10, max = 10)
  @NotNull(message = "Contact No is a mandatory field")
  private Long contact;

  //	Default Constructors
  public CustomerCO() {
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
