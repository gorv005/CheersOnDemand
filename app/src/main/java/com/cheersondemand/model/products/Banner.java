package com.cheersondemand.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Banner implements Serializable{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private Object subCategoryId;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * No args constructor for use in serialization
     *
     */
    public Banner() {
    }

    /**
     *
     * @param subCategoryId
     * @param id
     * @param categoryId
     * @param image
     */
    public Banner(Integer id, Integer categoryId, Object subCategoryId, String image) {
        super();
        this.id = id;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Object getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Object subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
