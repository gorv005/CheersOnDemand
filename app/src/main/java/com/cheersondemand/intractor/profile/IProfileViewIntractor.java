package com.cheersondemand.intractor.profile;

import android.content.Context;

import com.cheersondemand.model.logout.LogoutRequest;
import com.cheersondemand.model.logout.LogoutResponse;


/**
 * Created by AB on 7/31/2017.
 */

public interface IProfileViewIntractor {
    interface OnLoginFinishedListener {
        void onSuccess(LogoutResponse Response);
        void onError(String response);
        Context getAPPContext();
    }
    public void logout(LogoutRequest logoutRequest, OnLoginFinishedListener listener);



}
