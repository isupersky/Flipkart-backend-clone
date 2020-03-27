package com.tothenew.bluebox.bluebox.enitity.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

  @Id
  @GeneratedValue
  private Long id;
//  private Seller sellerUserId;
//  private String name;
//  private String description;
//  private Category categoryId;

//  ID
//      SELLER_USER_ID
//  NAME
//      DESCRIPTION
//"CATEGORY_ID
//    * Only the leaf category node would be associated with a product"
//  IS_CANCELLABLE
//      IS_RETURNABLE
//  BRAND
//      IS_ACTIVE


  public Product() {
  }
}
