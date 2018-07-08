package com.cheersondemand.model.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 6/18/2018.
 */

public class UpdateStore {
    @SerializedName("warehouse_id")
    @Expose
    private Integer warehouseId;
    @SerializedName("uuid")
    @Expose
    private String uuid;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
