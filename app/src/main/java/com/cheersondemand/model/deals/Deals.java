package com.cheersondemand.model.deals;

import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.Categories;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Deals implements Serializable{
    @SerializedName("categories")
    @Expose
    private List<Categories> categories = null;
    @SerializedName("products")
    @Expose
    private List<AllProduct> products = null;

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public List<AllProduct> getProducts() {
        return products;
    }

    public void setProducts(List<AllProduct> products) {
        this.products = products;
    }
}
