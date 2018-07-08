package com.cheersondemand.model.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 6/27/2018.
 */

public class CouponRequest {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("cart_id")
    @Expose
    private String cartId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}
