package com.cheersondemand.model.changepassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 7/5/2018.
 */

public class PasswordRequest {
    @SerializedName("user")
    @Expose
    private Password user;

    public Password getUser() {
        return user;
    }

    public void setUser(Password user) {
        this.user = user;
    }
}
