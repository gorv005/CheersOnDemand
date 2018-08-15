package com.cheersondemand.model.changepassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GAURAV on 8/15/2018.
 */

public class ResetPassword implements Serializable{
    @SerializedName("reset_password_token")
    @Expose
    private String resetPasswordToken;
    @SerializedName("password")
    @Expose
    private String password;

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
