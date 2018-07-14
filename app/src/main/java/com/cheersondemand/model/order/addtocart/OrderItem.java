package com.cheersondemand.model.order.addtocart;

import com.cheersondemand.model.AllProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 6/23/2018.
 */

public class OrderItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("old_unit_price")
    @Expose
    private String oldUnitPrice;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("is_deliverable")
    @Expose
    private Boolean isDeliverable;
    @SerializedName("remaining_stock_message")
    @Expose
    private String remainingStockMessage;
    @SerializedName("change")
    @Expose
    private String change;
    @SerializedName("product")
    @Expose
    private AllProduct product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getRemainingStockMessage() {
        return remainingStockMessage;
    }

    public void setRemainingStockMessage(String remainingStockMessage) {
        this.remainingStockMessage = remainingStockMessage;
    }

    public AllProduct getProduct() {
        return product;
    }

    public void setProduct(AllProduct product) {
        this.product = product;
    }

    public String getOldUnitPrice() {
        return oldUnitPrice;
    }

    public void setOldUnitPrice(String oldUnitPrice) {
        this.oldUnitPrice = oldUnitPrice;
    }



    public Boolean getIsDeliverable() {
        return isDeliverable;
    }

    public void setIsDeliverable(Boolean isDeliverable) {
        this.isDeliverable = isDeliverable;
    }

    public Boolean getDeliverable() {
        return isDeliverable;
    }

    public void setDeliverable(Boolean deliverable) {
        isDeliverable = deliverable;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
