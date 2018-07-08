package com.cheersondemand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AB on 6/15/2018.
 */

public class SubCategory implements Serializable {
    @SerializedName("pos")
    @Expose
    private Integer pos;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("is_selected")
    @Expose
    private boolean isSelected;

    public SubCategory() {
    }

    public SubCategory(Integer pos, Integer id, String name, Integer categoryId, boolean isSelected) {
        this.pos = pos;
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.isSelected = isSelected;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
