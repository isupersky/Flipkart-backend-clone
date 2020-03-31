package com.tothenew.bluebox.bluebox.enitity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
public class Seller extends User {

  //VALID GST NUMBER - 37adapm1724a2Z6
  @NotEmpty(message = "gst is a mandatory field")
  @Column(unique = true)
  @Pattern(regexp = "^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$",
      message = "GST should be valid as per Govt. norms")
  private Long gst;

  @NotEmpty(message = "Company Name is a mandatory field")
  @Column(unique = true)
  private String companyName;

  //This Pattern is to Validate Mobile Number with 10 digit Number and Countrycode as Optional, And check for Landline Numbers as well.
  @Pattern(regexp = "^(?:\\s+|)((0|(?:(\\+|)91))(?:\\s|-)*(?:(?:\\d(?:\\s|-)*\\d{9})|(?:\\d{2}(?:\\s|-)*\\d{8})|(?:\\d{3}(?:\\s|-)*\\d{7}))|\\d{10})(?:\\s+|)$"
      , message = "The Contact No. is not valid")
  @NotEmpty(message = "Company Contact No. is a mandatory field")
  private Long companyContact;

  //	Default constructor
  public Seller() {
  }

  public Long getGst() {
    return gst;
  }

  public void setGst(Long gst) {
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
    return "Seller{" +
        "gst=" + gst +
        ", companyName='" + companyName + '\'' +
        ", companyContact=" + companyContact +
        '}';
  }
}
