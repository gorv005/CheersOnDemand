package com.cheersondemand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 6/24/2018.
 */

public class Errors {
    @SerializedName("resource")
    @Expose
    private String resource;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("detail")
    @Expose
    private String detail;
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getField() {
        return field;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setField(String field) {
        this.field = field;
    }
}
