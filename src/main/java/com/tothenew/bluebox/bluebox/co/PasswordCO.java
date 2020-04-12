package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class PasswordCO {

  @NotEmpty(message = "Password is a mandatory field")
  @Length(min = 8, max = 15, message = "The Length of the password should be between 8 to 15 characters.")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*)(?=.*\\W.*)[a-zA-Z0-9\\S]{8,15}$",
      message = "The Password should be 8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
  private String oldPassword;

  @NotEmpty(message = "Password is a mandatory field")
  @Length(min = 8, max = 15, message = "The Length of the password should be between 8 to 15 characters.")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*)(?=.*\\W.*)[a-zA-Z0-9\\S]{8,15}$",
      message = "The Password should be 8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
  private String password;

  @NotEmpty(message = "Password is a mandatory field")
  @Length(min = 8, max = 15, message = "The Length of the password should be between 8 to 15 characters.")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*)(?=.*\\W.*)[a-zA-Z0-9\\S]{8,15}$",
      message = "The Password should be 8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
  private String rePassword;

  public PasswordCO() {
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRePassword() {
    return rePassword;
  }

  public void setRePassword(String rePassword) {
    this.rePassword = rePassword;
  }

  @Override
  public String toString() {
    return "PasswordCO{" +
        "oldPassword='" + oldPassword + '\'' +
        ", password='" + password + '\'' +
        ", rePassword='" + rePassword + '\'' +
        '}';
  }
}
