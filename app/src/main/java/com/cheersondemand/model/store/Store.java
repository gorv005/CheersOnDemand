package com.cheersondemand.model.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 6/18/2018.
 */

public class Store {
    @SerializedName("is_quantity_updated")
    @Expose
    private Boolean isQuantityUpdated;

    public Boolean getIsQuantityUpdated() {
        return isQuantityUpdated;
    }

    public void setIsQuantityUpdated(Boolean isQuantityUpdated) {
        this.isQuantityUpdated = isQuantityUpdated;
    }

}
