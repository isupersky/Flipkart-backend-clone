package com.tothenew.bluebox.bluebox.dto;

public class SellerDTO extends UserDTO {

  private String gst;

  private String companyName;

  private Long companyContact;

  private AddressDTO address;

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

  public AddressDTO getAddress() {
    return address;
  }

  public void setAddress(AddressDTO address) {
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
