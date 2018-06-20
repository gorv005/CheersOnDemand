package com.cheersondemand.model.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 6/4/2018.
 */

public class SocialLoginRequest {
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("loginType")
    @Expose
    private Integer loginType;
    @SerializedName("grant_type")
    @Expose
    private String grantType;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
