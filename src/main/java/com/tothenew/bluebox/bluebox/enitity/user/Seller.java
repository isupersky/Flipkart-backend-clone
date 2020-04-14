package com.tothenew.bluebox.bluebox.enitity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
public class Seller extends User {

  @Column(unique = true)
  private String gst;

  @Column(unique = true)
  private String companyName;

  private Long companyContact;


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
    return "Seller{" +
        "gst=" + gst +
        ", companyName='" + companyName + '\'' +
        ", companyContact=" + companyContact +
        '}';
  }
}
