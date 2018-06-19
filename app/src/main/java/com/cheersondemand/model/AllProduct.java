package com.cheersondemand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 6/15/2018.
 */

public class AllProduct {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("abv")
    @Expose
    private String abv;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("current_availability")
    @Expose
    private String currentAvailability;
    @SerializedName("manufacture_date")
    @Expose
    private String manufactureDate;
    @SerializedName("is_wishlisted")
    @Expose
    private Boolean isWishlisted;
    @SerializedName("cart_qunatity")
    @Expose
    private String cartQunatity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("is_in_cart")
    @Expose
    private Boolean isInCart;
    @SerializedName("sub_category")
    @Expose
    private SubCategory subCategory;
    @SerializedName("brand")
    @Expose
    private Brand brand;
    @SerializedName("product_type")
    @Expose
    private ProductType productType;
    @SerializedName("category")
    @Expose
    private Categories category;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrentAvailability() {
        return currentAvailability;
    }

    public void setCurrentAvailability(String currentAvailability) {
        this.currentAvailability = currentAvailability;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public Boolean getIsWishlisted() {
        return isWishlisted;
    }

    public void setIsWishlisted(Boolean isWishlisted) {
        this.isWishlisted = isWishlisted;
    }

    public String getCartQunatity() {
        return cartQunatity;
    }

    public void setCartQunatity(String cartQunatity) {
        this.cartQunatity = cartQunatity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getIsInCart() {
        return isInCart;
    }

    public void setIsInCart(Boolean isInCart) {
        this.isInCart = isInCart;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

}