package com.cheersondemand.model.order.addtocart;

import com.cheersondemand.model.Errors;
import com.cheersondemand.model.productdescription.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GAURAV on 6/30/2018.
 */

public class CartHasItemResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private CartHasItem data;
    @SerializedName("meta")
    @Expose
    private List<Meta> meta = null;
    @SerializedName("errors")
    @Expose
    private List<Errors> errors = null;

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

    public CartHasItem getData() {
        return data;
    }

    public void setData(CartHasItem data) {
        this.data = data;
    }

    public List<Meta> getMeta() {
        return meta;
    }

    public void setMeta(List<Meta> meta) {
        this.meta = meta;
    }

    public List<Errors> getErrors() {
        return errors;
    }

    public void setErrors(List<Errors> errors) {
        this.errors = errors;
    }

}
