package com.cheersondemand.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 7/8/2018.
 */

public class Product {
    @SerializedName("class_id")
    @Expose
    private Integer classId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("class_name")
    @Expose
    private String className;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
