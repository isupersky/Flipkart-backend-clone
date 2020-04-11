package com.tothenew.bluebox.bluebox.co;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SellerProfileUpdate extends UserProfileUpdate {

  @NotEmpty(message = "gst is a mandatory field")
  @Column(unique = true)
  private String gst;

  @NotEmpty(message = "Company Name is a mandatory field")
  @Column(unique = true)
  private String companyName;

  @NotNull(message = "Company Contact No. is a mandatory field")
  private Long companyContact;

  public SellerProfileUpdate() {
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

  @Override
  public String toString() {
    return "SellerProfileUpdate{" +
        "gst='" + gst + '\'' +
        ", companyName='" + companyName + '\'' +
        ", companyContact=" + companyContact +
        '}';
  }
}
