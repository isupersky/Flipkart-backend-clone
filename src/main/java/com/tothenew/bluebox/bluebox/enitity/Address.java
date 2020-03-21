package com.tothenew.bluebox.bluebox.enitity;

import java.util.UUID;

public class Address {

  private UUID id;
  private String city;
  private String state;
  private String country;
  private String address;
  private Integer zipCode;
  private String label;
  private UUID customerUserId;

  //	Default constructor
  public Address() {
  }

  //	Getters and Setters
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public UUID getCustomerUserId() {
    return customerUserId;
  }

  public void setCustomerUserId(UUID customerUserId) {
    this.customerUserId = customerUserId;
  }

  @Override
  public String toString() {
    return "Address{" +
        "id=" + id +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", country='" + country + '\'' +
        ", address='" + address + '\'' +
        ", zipCode=" + zipCode +
        ", label='" + label + '\'' +
        ", customerUserId=" + customerUserId +
        '}';
  }
}
