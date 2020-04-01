package com.tothenew.bluebox.bluebox.enitity.order;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

//----------------------------------------COMPLETE-------------------------------------

@Entity
public class OrderStatus {

  @Id
  private Long id;
  @OneToOne
  @JoinColumn(name = "orderProductId")
  private OrderProduct orderProductId;
  private String fromStatus;
  private String toStatus;
  private String transitionNotesComments;

  public OrderStatus() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OrderProduct getOrderProductId() {
    return orderProductId;
  }

  public void setOrderProductId(OrderProduct orderProductId) {
    this.orderProductId = orderProductId;
  }

  public String getFromStatus() {
    return fromStatus;
  }

  public void setFromStatus(String fromStatus) {
    this.fromStatus = fromStatus;
  }

  public String getToStatus() {
    return toStatus;
  }

  public void setToStatus(String toStatus) {
    this.toStatus = toStatus;
  }

  public String getTransitionNotesComments() {
    return transitionNotesComments;
  }

  public void setTransitionNotesComments(String transitionNotesComments) {
    this.transitionNotesComments = transitionNotesComments;
  }

  @Override
  public String toString() {
    return "OrderStatus{" +
        "id=" + id +
        ", orderProductId=" + orderProductId +
        ", fromStatus='" + fromStatus + '\'' +
        ", toStatus='" + toStatus + '\'' +
        ", transitionNotesComments='" + transitionNotesComments + '\'' +
        '}';
  }
}
