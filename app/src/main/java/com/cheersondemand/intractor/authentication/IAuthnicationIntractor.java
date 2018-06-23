package com.cheersondemand.intractor.authentication;

import android.content.Context;

import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.authentication.LoginRequest;
import com.cheersondemand.model.authentication.SignUpRequest;
import com.cheersondemand.model.authentication.SocialLoginRequest;


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
    public void createGuestUser(GenRequest categoryRequest, OnLoginFinishedListener listener);

}
