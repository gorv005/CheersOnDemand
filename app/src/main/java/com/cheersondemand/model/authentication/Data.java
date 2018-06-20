package com.cheersondemand.model.authentication;

import com.cheersondemand.model.UserResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 5/30/2018.
 */

public class Data {
    @SerializedName("token")
    @Expose
    private Token token;
    @SerializedName("user")
    @Expose
    private UserResponse user;

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
}
