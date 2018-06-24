package com.cheersondemand.model.wishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 6/25/2018.
 */

public class WishListRequest {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("product_id")
    @Expose
    private Integer productId;

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

}
