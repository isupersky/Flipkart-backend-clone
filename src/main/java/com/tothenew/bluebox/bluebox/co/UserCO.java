package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public abstract class UserCO {

  @Email(message = "This is not a valid Email")
  @NotEmpty(message = "Email field can not be Empty")
  private String email;

  @NotEmpty(message = "First Name Field can not be Empty")
  private String firstName;

  private String middleName;

  @NotEmpty(message = "Last Name Field can not be Empty")
  private String lastName;

  @NotEmpty(message = "Password is a mandatory field")
  @Length(min = 8, max = 15, message = "The Length of the password should be between 8 to 15 characters.")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*)(?=.*\\W.*)[a-zA-Z0-9\\S]{8,15}$",
      message = "The Password should be 8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
  private String password;


  //	Default Constructor
  public UserCO() {
  }

//	Getters And setters

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserCO{" +
        "email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", middleName='" + middleName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
