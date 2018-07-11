package com.cheersondemand.intractor.profile;

import android.content.Context;

import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.logout.LogoutRequest;
import com.cheersondemand.model.logout.LogoutResponse;
import com.cheersondemand.model.profile.ProfileUpdateRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by AB on 7/31/2017.
 */

public interface IProfileViewIntractor {
    interface OnLoginFinishedListener {
        void onSuccess(LogoutResponse Response);
        void onSuccessUpdateProfile(GuestUserCreateResponse Response);

        void onError(String response);
        Context getAPPContext();
    }
    public void logout(LogoutRequest logoutRequest, OnLoginFinishedListener listener);
    public void updateProfile(String token, String UserId, MultipartBody.Part part, RequestBody name, RequestBody phone, OnLoginFinishedListener listener);
    public void updateProfile(String token, String userId, ProfileUpdateRequest profileUpdateRequest, OnLoginFinishedListener listener);



}
