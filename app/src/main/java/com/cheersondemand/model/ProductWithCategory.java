package com.cheersondemand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GAURAV on 6/15/2018.
 */

public class ProductWithCategory implements Serializable{

    @SerializedName("has_cart_product")
    @Expose
    private Boolean hasCartProduct;
    @SerializedName("categories")
    @Expose
    private List<Categories> categories = null;
    @SerializedName("all_products")
    @Expose
    private List<AllProduct> allProducts = null;
    @SerializedName("user")
    @Expose
    private UserResponse user;

    public Boolean getHasCartProduct() {
        return hasCartProduct;
    }

    public void setHasCartProduct(Boolean hasCartProduct) {
        this.hasCartProduct = hasCartProduct;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public List<AllProduct> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<AllProduct> allProducts) {
        this.allProducts = allProducts;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
