package com.cheersondemand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 7/15/2018.
 */

public class DeliveryAddress {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("flat_no")
    @Expose
    private String flatNo;
    @SerializedName("address_first")
    @Expose
    private String addressFirst;
    @SerializedName("address_second")
    @Expose
    private String addressSecond;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getAddressFirst() {
        return addressFirst;
    }

    public void setAddressFirst(String addressFirst) {
        this.addressFirst = addressFirst;
    }

    public String getAddressSecond() {
        return addressSecond;
    }

    public void setAddressSecond(String addressSecond) {
        this.addressSecond = addressSecond;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
