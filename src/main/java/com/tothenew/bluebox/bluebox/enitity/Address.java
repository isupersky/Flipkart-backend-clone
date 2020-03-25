package com.tothenew.bluebox.bluebox.enitity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Address {

  @Id
  @GeneratedValue
  private Long id;
  private String city;
  private String state;
  private String country;
  private String address;
  private Integer zipCode;
  private String label;
  @ManyToOne
//  @JoinColumn(name = "id")
  private User user;

  //	Default constructor
  public Address() {
  }

  //	Getters and Setters


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
        ", user=" + user +
        '}';
  }
}
