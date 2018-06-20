package com.cheersondemand.intractor.authentication;

import android.util.Log;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.model.authentication.CategoryRequest;
import com.cheersondemand.model.authentication.LoginRequest;
import com.cheersondemand.model.authentication.SignUpRequest;
import com.cheersondemand.model.authentication.SocialLoginRequest;
import com.cheersondemand.util.C;

import retrofit2.Response;

/**
 * Created by GAURAV on 7/31/2017.
 */

public class AuthniticationIntractorImpl implements IAuthnicationIntractor {


    @Override
    public void loginUsingEmail(LoginRequest loginRequest, final OnLoginFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().loginUsingEmail(new ResponseResolver<AuthenticationResponse>() {
                @Override
                public void onSuccess(AuthenticationResponse signUpResponse, Response response) {
                    listener.onSuccess(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error.getError()==null){
                        listener.onError(error.getMessage());

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },loginRequest);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getResponse(SignUpRequest signUpRequest, final OnLoginFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().signUp(new ResponseResolver<AuthenticationResponse>() {
                @Override
                public void onSuccess(AuthenticationResponse signUpResponse, Response response) {
                    listener.onSuccess(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error.getError()==null){
                        listener.onError(error.getMessage());

                    }
                    else {
                        listener.onError(error.getError());
                    }                }
            },signUpRequest);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseSocail(SocialLoginRequest signUpRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().loginUsingSocial(new ResponseResolver<AuthenticationResponse>() {
                @Override
                public void onSuccess(AuthenticationResponse signUpResponse, Response response) {
                    listener.onSuccess(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    Log.e("dd","hh");
                    if(error.getError()==null){
                        listener.onError(error.getMessage());

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },signUpRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createGuestUser(CategoryRequest categoryRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().createGuestUser(new ResponseResolver<GuestUserCreateResponse>() {
                @Override
                public void onSuccess(GuestUserCreateResponse signUpResponse, Response response) {
                    listener.onSuccessCreateGuestuser(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    Log.e("dd","hh");




                        if(error!=null &&error.getMessage()!=null &&error.getMessage().trim().equalsIgnoreCase(C.GUEST_USER_ALLREADY_CREATED)) {
                            GuestUserCreateResponse guestUserCreateResponse = new GuestUserCreateResponse();
                            guestUserCreateResponse.setSuccess(true);
                            guestUserCreateResponse.setMessage(error.getMessage().trim());
                            guestUserCreateResponse.setData(error.getData());
                            onSuccess(guestUserCreateResponse, null);
                        }
                    if(error.getError()==null){
                        listener.onError(error.getMessage());

                    }
                        else {
                            listener.onError(error.getError());
                        }

                }
            },categoryRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
