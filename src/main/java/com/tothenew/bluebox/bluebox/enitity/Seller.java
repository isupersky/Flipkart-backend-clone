package com.tothenew.bluebox.bluebox.enitity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
public class Seller extends User {

  private Long gst;
  private String companyName;
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
