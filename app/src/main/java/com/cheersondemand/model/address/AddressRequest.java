package com.cheersondemand.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AB on 7/6/2018.
 */

public class AddressRequest implements Serializable {
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("uuid")
    @Expose
    private String uuid;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
