package com.cheersondemand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GAURAV on 6/11/2018.
 */

public class Categories implements Serializable {
    @SerializedName("pos")
    @Expose
    private Integer pos;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("products_count")
    @Expose
    private Integer productsCount;
    @SerializedName("is_selected")
    @Expose
    private boolean isSelected=false;

    public Categories() {
    }


    public Categories(Integer pos, Integer id, String name, String image, Integer productsCount, boolean isSelected) {
        this.pos = pos;
        this.id = id;
        this.name = name;
        this.image = image;
        this.productsCount = productsCount;
        this.isSelected = isSelected;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }
}
