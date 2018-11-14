package com.cheersondemand.model;

import com.cheersondemand.model.products.Banner;
import com.cheersondemand.model.products.OnSaleProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AB on 6/15/2018.
 */

public class ProductWithCategory implements Serializable{

    @SerializedName("has_cart_product")
    @Expose
    private Boolean hasCartProduct;
    @SerializedName("banner")
    @Expose
    private Banner banner;
    @SerializedName("categories")
    @Expose
    private List<Categories> categories = null;
    @SerializedName("all_products")
    @Expose
    private List<AllProduct> allProducts = null;
    @SerializedName("user")
    @Expose
    private UserResponse user;
    @SerializedName("on_sale_products")
    @Expose
    private List<AllProduct> onSaleProducts = null;
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

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public List<AllProduct> getOnSaleProducts() {
        return onSaleProducts;
    }

    public void setOnSaleProducts(List<AllProduct> onSaleProducts) {
        this.onSaleProducts = onSaleProducts;
    }
}
