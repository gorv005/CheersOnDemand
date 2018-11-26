package com.cheersondemand.model.explore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoriesList implements Serializable{
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
    @SerializedName("sub_categories")
    @Expose
    private List<SubCategoryExplore> subCategories = null;

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

    public List<SubCategoryExplore> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategoryExplore> subCategories) {
        this.subCategories = subCategories;
    }

}
