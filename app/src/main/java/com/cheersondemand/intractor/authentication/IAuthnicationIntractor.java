package com.cheersondemand.intractor.authentication;

import android.content.Context;

import com.cheersondemand.model.CategoryRequest;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.LoginRequest;
import com.cheersondemand.model.SignUpRequest;
import com.cheersondemand.model.AuthenticationResponse;
import com.cheersondemand.model.SocialLoginRequest;

/**
 * Created by GAURAV on 7/31/2017.
 */

public interface IAuthnicationIntractor {
    interface OnLoginFinishedListener {
        void onSuccess(AuthenticationResponse signUpResponse);
        void onSuccessCreateGuestuser(GuestUserCreateResponse signUpResponse);

        void onError(String response);
        Context getAPPContext();
    }
    public void loginUsingEmail(LoginRequest loginRequest, OnLoginFinishedListener listener);

    public void getResponse(SignUpRequest signUpRequest, OnLoginFinishedListener listener);
    public void getResponseSocail(SocialLoginRequest socialLoginRequest, OnLoginFinishedListener listener);
    public void createGuestUser(CategoryRequest categoryRequest, OnLoginFinishedListener listener);

}
