package com.cheersondemand.model.logout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 5/30/2018.
 */

public class LogoutRequest {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
