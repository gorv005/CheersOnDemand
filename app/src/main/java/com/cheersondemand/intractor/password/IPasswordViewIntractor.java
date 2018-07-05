package com.cheersondemand.intractor.password;

import android.content.Context;

import com.cheersondemand.model.changepassword.PasswordRequest;
import com.cheersondemand.model.changepassword.PasswordResponse;

/**
 * Created by GAURAV on 7/5/2018.
 */

public interface IPasswordViewIntractor {
    interface OnLoginFinishedListener {
        void onPasswordRequestSuccess(PasswordResponse Response);

        void onError(String response);
        Context getAPPContext();
    }
    public void changePassword(String token,String userId, PasswordRequest passwordRequest, OnLoginFinishedListener listener);
    public void forgotPassword(PasswordRequest passwordRequest, OnLoginFinishedListener listener);




}
