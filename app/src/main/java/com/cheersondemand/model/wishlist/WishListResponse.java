package com.cheersondemand.model.wishlist;

import com.cheersondemand.model.AllProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cheersondemand.model.Errors;
import java.util.List;

/**
 * Created by GAURAV on 6/25/2018.
 */

public class WishListResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private AllProduct data = null;
    @SerializedName("meta")
    @Expose
    private List<Object> meta = null;
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

    public AllProduct getData() {
        return data;
    }

    public void setData(AllProduct data) {
        this.data = data;
    }

    public List<Object> getMeta() {
        return meta;
    }

    public void setMeta(List<Object> meta) {
        this.meta = meta;
    }

    public List<Errors> getErrors() {
        return errors;
    }

    public void setErrors(List<Errors> errors) {
        this.errors = errors;
    }
}
