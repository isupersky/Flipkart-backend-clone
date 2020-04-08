package com.tothenew.bluebox.bluebox.dto;

import javax.validation.constraints.NotEmpty;

public class AddressDto {

  @NotEmpty(message = "city field is missing")
  private String city;
  @NotEmpty(message = "state field is missing")
  private String state;
  @NotEmpty(message = "country field is missing")
  private String country;
  @NotEmpty(message = "address Line field is missing")
  private String addressLine;
  @NotEmpty(message = "Zip Code field is missing")
  private Integer zipCode;
  @NotEmpty(message = "label field is missing")
  private String label;


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getAddressLine() {
    return addressLine;
  }

  public void setAddressLine(String addressLine) {
    this.addressLine = addressLine;
  }

  public Integer getZipCode() {
    return zipCode;
  }

  public void setZipCode(Integer zipCode) {
    this.zipCode = zipCode;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return "AddressDto{" +
        "city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", country='" + country + '\'' +
        ", addressLine='" + addressLine + '\'' +
        ", zipCode=" + zipCode +
        ", label='" + label + '\'' +
        '}';
  }
}
