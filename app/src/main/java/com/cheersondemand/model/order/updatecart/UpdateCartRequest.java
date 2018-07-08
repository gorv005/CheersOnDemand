package com.cheersondemand.model.order.updatecart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 6/23/2018.
 */

public class UpdateCartRequest {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("is_increase")
    @Expose
    private Boolean isIncrease;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsIncrease() {
        return isIncrease;
    }

    public void setIsIncrease(Boolean isIncrease) {
        this.isIncrease = isIncrease;
    }
}
