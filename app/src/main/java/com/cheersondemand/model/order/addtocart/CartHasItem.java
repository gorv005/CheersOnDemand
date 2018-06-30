package com.cheersondemand.model.order.addtocart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 6/30/2018.
 */

public class CartHasItem {
    @SerializedName("has_cart_product")
    @Expose
    private Boolean hasCartProduct;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;

    public Boolean getHasCartProduct() {
        return hasCartProduct;
    }

    public void setHasCartProduct(Boolean hasCartProduct) {
        this.hasCartProduct = hasCartProduct;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
