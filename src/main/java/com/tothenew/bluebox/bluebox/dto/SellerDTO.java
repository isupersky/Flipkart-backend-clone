package com.tothenew.bluebox.bluebox.dto;

import com.tothenew.bluebox.bluebox.enitity.user.Address;
import java.util.Set;

public class SellerDTO extends UserDTO {

  private String gst;

  private String companyName;

  private Long companyContact;

  private Set<Address> address;

  public SellerDTO() {
  }

  public String getGst() {
    return gst;
  }

  public void setGst(String gst) {
    this.gst = gst;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Long getCompanyContact() {
    return companyContact;
  }

  public void setCompanyContact(Long companyContact) {
    this.companyContact = companyContact;
  }

  public Set<Address> getAddress() {
    return address;
  }

  public void setAddress(Set<Address> address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "SellerDTO{" +
        "gst='" + gst + '\'' +
        ", companyName='" + companyName + '\'' +
        ", companyContact=" + companyContact +
        ", address=" + address +
        '}';
  }
}
