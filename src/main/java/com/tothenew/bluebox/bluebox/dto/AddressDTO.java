package com.tothenew.bluebox.bluebox.dto;

public class AddressDTO {


  private String city;

  private String state;

  private String country;

  private String addressLine;

  private Integer zipCode;

  private String label;

  public AddressDTO() {
  }

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
    return "AddressDTO{" +
        "city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", country='" + country + '\'' +
        ", addressLine='" + addressLine + '\'' +
        ", zipCode=" + zipCode +
        ", label='" + label + '\'' +
        '}';
  }
}
