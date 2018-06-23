package com.cheersondemand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 6/24/2018.
 */

public class Error {
    @SerializedName("resource")
    @Expose
    private String resource;
    @SerializedName("field")
    @Expose
    private String field;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
