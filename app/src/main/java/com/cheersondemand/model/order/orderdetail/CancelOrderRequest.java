package com.cheersondemand.model.order.orderdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 7/17/2018.
 */

public class CancelOrderRequest {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("cancellation_reason")
    @Expose
    private String cancellationReason;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

}
