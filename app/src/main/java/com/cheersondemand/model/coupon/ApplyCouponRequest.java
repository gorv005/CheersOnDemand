package com.cheersondemand.model.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 6/27/2018.
 */

public class ApplyCouponRequest {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("cart_value")
    @Expose
    private String cartValue;
    @SerializedName("cart_id")
    @Expose
    private String cartId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCartValue() {
        return cartValue;
    }

    public void setCartValue(String cartValue) {
        this.cartValue = cartValue;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}
