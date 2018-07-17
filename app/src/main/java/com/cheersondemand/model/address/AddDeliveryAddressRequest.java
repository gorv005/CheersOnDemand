package com.cheersondemand.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AB on 7/6/2018.
 */

public class AddDeliveryAddressRequest implements Serializable {
    @SerializedName("delivery_address_id")
    @Expose
    private String deliveryAddressId;

    public String getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(String deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }
}
