package com.cheersondemand.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 6/23/2018.
 */

public class OrderRes {
    @SerializedName("order")
    @Expose
    private OrderInfo order;

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
