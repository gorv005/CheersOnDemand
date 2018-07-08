package com.cheersondemand.model.order.updatecart;

import com.cheersondemand.model.order.addtocart.CartProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AB on 6/23/2018.
 */

public class UpdateCartResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private CartProduct data;
    @SerializedName("meta")
    @Expose
    private List<Object> meta = null;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CartProduct getData() {
        return data;
    }

    public void setData(CartProduct data) {
        this.data = data;
    }

    public List<Object> getMeta() {
        return meta;
    }

    public void setMeta(List<Object> meta) {
        this.meta = meta;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

}
