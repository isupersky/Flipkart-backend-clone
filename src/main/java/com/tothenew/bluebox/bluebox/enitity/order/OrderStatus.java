package com.tothenew.bluebox.bluebox.enitity.order;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

//----------------------------------------COMPLETE-------------------------------------

@Entity
public class OrderStatus implements Serializable {

  @Id
  @OneToOne
  @JoinColumn(name = "orderProductId")
  private OrderProduct orderProductId;
  private String fromStatus;
  private String toStatus;
  private String transitionNotesComments;

  public OrderStatus() {
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
        "orderProductId=" + orderProductId +
        ", fromStatus='" + fromStatus + '\'' +
        ", toStatus='" + toStatus + '\'' +
        ", transitionNotesComments='" + transitionNotesComments + '\'' +
        '}';
  }
}
