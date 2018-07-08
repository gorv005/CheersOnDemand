package com.cheersondemand.model.order.addtocart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 6/23/2018.
 */

public class CartProduct {

    @SerializedName("coupon_message")
    @Expose
    private String couponMessage;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("order")
    @Expose
    private Order order;

    public String getCouponMessage() {
        return couponMessage;
    }

    public void setCouponMessage(String couponMessage) {
        this.couponMessage = couponMessage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
