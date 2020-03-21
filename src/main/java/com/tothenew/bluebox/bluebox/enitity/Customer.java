package com.tothenew.bluebox.bluebox.enitity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
public class Customer extends User {

  //	@Column(name = "id")
//	private UUID userId;
  private Long contact;

  //	Default Constructors
  public Customer() {
  }

//	Getters and Setters
//	public UUID getUserId() {
//		return userId;
//	}

//	public void setUserId(UUID userId) {
//		this.userId = userId;
//	}

  public Long getContact() {
    return contact;
  }

  public void setContact(Long contact) {
    this.contact = contact;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "contact=" + contact +
        '}';
  }
}
