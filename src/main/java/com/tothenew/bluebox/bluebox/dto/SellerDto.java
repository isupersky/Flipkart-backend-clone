package com.tothenew.bluebox.bluebox.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SellerDto extends UserDto {

  //VALID GST NUMBER - 37adapm1724a2Z6
  @NotEmpty(message = "gst is a mandatory field")
  @Column(unique = true)
//  @Pattern(regexp = "^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$",
//      message = "GST should be valid as per Govt. norms")
  private String gst;

  @NotEmpty(message = "Company Name is a mandatory field")
  @Column(unique = true)
  private String companyName;

  //This Pattern is to Validate Mobile Number with 10 digit Number and Countrycode as Optional, And check for Landline Numbers as well.
//  @Pattern(regexp = "^(?:\\s+|)((0|(?:(\\+|)91))(?:\\s|-)*(?:(?:\\d(?:\\s|-)*\\d{9})|(?:\\d{2}(?:\\s|-)*\\d{8})|(?:\\d{3}(?:\\s|-)*\\d{7}))|\\d{10})(?:\\s+|)$"
//      , message = "The Contact No. is not valid")
  @NotNull(message = "Company Contact No. is a mandatory field")
  private Long companyContact;

  private AddressDto companyAddress;


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

  public AddressDto getCompanyAddress() {
    return companyAddress;
  }

  public void setCompanyAddress(AddressDto companyAddress) {
    this.companyAddress = companyAddress;
  }

  @Override
  public String toString() {
    return "SellerDto{" +
        "gst='" + gst + '\'' +
        ", companyName='" + companyName + '\'' +
        ", companyContact=" + companyContact +
        ", companyAddress=" + companyAddress +
        '}';
  }
}
