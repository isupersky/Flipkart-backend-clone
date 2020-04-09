package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailCO {

  @Email(message = "This is not a valid Email")
  @NotEmpty(message = "Email field can not be Empty")
  private String email;

  public EmailCO() {
  }

  public EmailCO(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "EmailCO{" +
        "email='" + email + '\'' +
        '}';
  }
}