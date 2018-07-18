package com.cheersondemand.model.order.updatecart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AB on 6/24/2018.
 */

public class Coupon implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("valid_from")
    @Expose
    private String validFrom;
    @SerializedName("valid_to")
    @Expose
    private String validTo;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("min_cart_value")
    @Expose
    private String minCartValue;
    @SerializedName("max_cart_value")
    @Expose
    private String maxCartValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMinCartValue() {
        return minCartValue;
    }

    public void setMinCartValue(String minCartValue) {
        this.minCartValue = minCartValue;
    }

    public String getMaxCartValue() {
        return maxCartValue;
    }

    public void setMaxCartValue(String maxCartValue) {
        this.maxCartValue = maxCartValue;
    }
}
