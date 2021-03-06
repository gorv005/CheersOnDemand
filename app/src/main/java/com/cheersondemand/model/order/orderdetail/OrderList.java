package com.cheersondemand.model.order.orderdetail;


import com.cheersondemand.model.order.addtocart.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderList {
    @SerializedName("order")
    @Expose
    private List<Order> order = null;
    @SerializedName("order_id")
    @Expose
    private String orderId = null;
    public List<Order> getOrder() {
        return order;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
