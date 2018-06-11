package com.cheersondemand.intractor;

import android.content.Context;

import com.cheersondemand.model.LogoutRequest;

/**
 * Created by GAURAV on 7/31/2017.
 */

public interface IProfileViewIntractor {
    interface OnLoginFinishedListener {
        void onSuccess(String Response);
        void onError(String response);
        Context getAPPContext();
    }
    public void logout(LogoutRequest logoutRequest, OnLoginFinishedListener listener);



}
