package com.cheersondemand.model.products;

import com.cheersondemand.model.Brand;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.ProductType;
import com.cheersondemand.model.SubCategory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnSaleProduct {
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
    private String cartQuantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("is_in_cart")
    @Expose
    private Boolean isInCart;
    @SerializedName("on_sale")
    @Expose
    private Boolean onSale;
    @SerializedName("sale_price")
    @Expose
    private String salePrice;
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

    /**
     * No args constructor for use in serialization
     *
     */
    public OnSaleProduct() {
    }

/**
 *
 * @param region
 * @param onSale
 * @param subCategory
 * @param cartQuantity
 * @param image
 * @param productType
 * @param id
 * @param category
 * @param price
 * @param abv
 * @param description
 * @param name
 * @param brand
 * @param salePrice
 * @param quantity
 * @param isWishlisted
 * @param manufactureDate
 * @param isInCart
 * @param currentAvailability
 */

    public OnSaleProduct(Integer id, String name, String image, String description, String abv, String region, String currentAvailability, String manufactureDate, String quantity, Boolean isWishlisted, String cartQuantity, String price, Boolean isInCart, Boolean onSale, String salePrice, SubCategory subCategory, Brand brand, ProductType productType, Categories category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.abv = abv;
        this.region = region;
        this.currentAvailability = currentAvailability;
        this.manufactureDate = manufactureDate;
        this.quantity = quantity;
        this.isWishlisted = isWishlisted;
        this.cartQuantity = cartQuantity;
        this.price = price;
        this.isInCart = isInCart;
        this.onSale = onSale;
        this.salePrice = salePrice;
        this.subCategory = subCategory;
        this.brand = brand;
        this.productType = productType;
        this.category = category;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getWishlisted() {
        return isWishlisted;
    }

    public void setWishlisted(Boolean wishlisted) {
        isWishlisted = wishlisted;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getInCart() {
        return isInCart;
    }

    public void setInCart(Boolean inCart) {
        isInCart = inCart;
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
