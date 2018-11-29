package com.cheersondemand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AB on 6/15/2018.
 */

public class AllProduct implements Serializable {

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
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("is_wishlisted")
    @Expose
    private Boolean isWishlisted;
    @SerializedName("cart_quantity")
    @Expose
    private String cartQunatity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("is_deliverable")
    @Expose
    private Boolean isDeliverable;
    @SerializedName("is_in_cart")
    @Expose
    private Boolean isInCart;
    @SerializedName("on_sale")
    @Expose
    private Boolean onSale;
    @SerializedName("sale_price")
    @Expose
    private String salePrice;
    @SerializedName("discount")
    @Expose
    private String discount;
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

    public Boolean getWishlisted() {
        return isWishlisted;
    }

    public void setWishlisted(Boolean wishlisted) {
        isWishlisted = wishlisted;
    }

    public Boolean getInCart() {
        return isInCart;
    }

    public void setInCart(Boolean inCart) {
        isInCart = inCart;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getDeliverable() {
        return isDeliverable;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setDeliverable(Boolean deliverable) {
        isDeliverable = deliverable;
    }
}
