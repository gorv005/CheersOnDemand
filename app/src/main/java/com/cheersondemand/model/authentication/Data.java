package com.cheersondemand.model.authentication;

import com.cheersondemand.model.UserResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 5/30/2018.
 */

public class Data {
    @SerializedName("token")
    @Expose
    private Token token;
    @SerializedName("user")
    @Expose
    private UserResponse user;
    @SerializedName("current_location")
    @Expose
    private CurrentLocation currentLocation;
    @SerializedName("is_store_available")
    @Expose
    private Boolean isStoreAvailable;
    @SerializedName("store")
    @Expose
    private Store store;
    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Boolean getStoreAvailable() {
        return isStoreAvailable;
    }

    public void setStoreAvailable(Boolean storeAvailable) {
        isStoreAvailable = storeAvailable;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
