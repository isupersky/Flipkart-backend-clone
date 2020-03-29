package com.tothenew.bluebox.bluebox.enitity.order;

import com.tothenew.bluebox.bluebox.enitity.Customer;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

//----------------------------------------COMPLETE-------------------------------------

@Entity
public class Orders {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name = "customerId")
  @NotNull
  private Customer customerId;
  private Float amountPaid;
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateCreated;
  private String paymentMethod;
  private String customerAddressCity;
  private String customerAddressState;
  private String customerAddressCountry;
  private String customerAddressAddressLine;
  private Integer customerZipCode;
  private String customerAddressLabel;

  public Orders() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Customer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Customer customerId) {
    this.customerId = customerId;
  }

  public Float getAmountPaid() {
    return amountPaid;
  }

  public void setAmountPaid(Float amountPaid) {
    this.amountPaid = amountPaid;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getCustomerAddressCity() {
    return customerAddressCity;
  }

  public void setCustomerAddressCity(String customerAddressCity) {
    this.customerAddressCity = customerAddressCity;
  }

  public String getCustomerAddressState() {
    return customerAddressState;
  }

  public void setCustomerAddressState(String customerAddressState) {
    this.customerAddressState = customerAddressState;
  }

  public String getCustomerAddressCountry() {
    return customerAddressCountry;
  }

  public void setCustomerAddressCountry(String customerAddressCountry) {
    this.customerAddressCountry = customerAddressCountry;
  }

  public String getCustomerAddressAddressLine() {
    return customerAddressAddressLine;
  }

  public void setCustomerAddressAddressLine(String customerAddressAddressLine) {
    this.customerAddressAddressLine = customerAddressAddressLine;
  }

  public Integer getCustomerZipCode() {
    return customerZipCode;
  }

  public void setCustomerZipCode(Integer customerZipCode) {
    this.customerZipCode = customerZipCode;
  }

  public String getCustomerAddressLabel() {
    return customerAddressLabel;
  }

  public void setCustomerAddressLabel(String customerAddressLabel) {
    this.customerAddressLabel = customerAddressLabel;
  }

  @Override
  public String toString() {
    return "Orders{" +
        "id=" + id +
        ", customerId=" + customerId +
        ", amountPaid=" + amountPaid +
        ", dateCreated=" + dateCreated +
        ", paymentMethod='" + paymentMethod + '\'' +
        ", customerAddressCity='" + customerAddressCity + '\'' +
        ", customerAddressState='" + customerAddressState + '\'' +
        ", customerAddressCountry='" + customerAddressCountry + '\'' +
        ", customerAddressAddressLine='" + customerAddressAddressLine + '\'' +
        ", customerZipCode=" + customerZipCode +
        ", customerAddressLabel='" + customerAddressLabel + '\'' +
        '}';
  }
}
