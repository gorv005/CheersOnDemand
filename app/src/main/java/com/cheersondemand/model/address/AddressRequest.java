package com.cheersondemand.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GAURAV on 7/6/2018.
 */

public class AddressRequest implements Serializable {
    @SerializedName("address")
    @Expose
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
