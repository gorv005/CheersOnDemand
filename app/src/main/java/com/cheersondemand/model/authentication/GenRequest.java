package com.cheersondemand.model.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 5/30/2018.
 */

public class GenRequest {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("warehouse_id")
    @Expose
    private String warehouseId;
    public GenRequest() {
    }
    public GenRequest(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
}
