package com.tothenew.bluebox.bluebox.enitity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "userId",referencedColumnName = "id")
public class Seller extends User{
//	@Column(name = "id")
//	private UUID userId;
	private Long gst;
	private String companyName;
	private Long companyContact;
	private UUID companyAddressId;
	
//	Default constructor
	public Seller() {	}
	
//	Getters and Setters
//	public UUID getUserId() {
//		return userId;
//	}
	
//	public void setUserId(UUID userId) {
//		this.userId = userId;
//	}
	
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
	
	public UUID getCompanyAddressId() {
		return companyAddressId;
	}
	
	public void setCompanyAddressId(UUID companyAddressId) {
		this.companyAddressId = companyAddressId;
	}

	@Override
	public String toString() {
		return "Seller{" +
				"gst=" + gst +
				", companyName='" + companyName + '\'' +
				", companyContact=" + companyContact +
				", companyAddressId=" + companyAddressId +
				'}';
	}
}
