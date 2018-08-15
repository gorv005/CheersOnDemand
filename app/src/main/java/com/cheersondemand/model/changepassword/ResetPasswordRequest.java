package com.cheersondemand.model.changepassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AB on 8/15/2018.
 */

public class ResetPasswordRequest implements Serializable{
    @SerializedName("user")
    @Expose
    private ResetPassword user;

    public ResetPassword getUser() {
        return user;
    }

    public void setUser(ResetPassword user) {
        this.user = user;
    }

}