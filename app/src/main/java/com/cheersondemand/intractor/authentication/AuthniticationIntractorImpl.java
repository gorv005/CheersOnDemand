package com.cheersondemand.intractor.authentication;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.authentication.LoginRequest;
import com.cheersondemand.model.authentication.SignUpRequest;
import com.cheersondemand.model.authentication.SocialLoginRequest;
import com.google.gson.Gson;

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
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AuthenticationResponse response= gson.fromJson(msg,AuthenticationResponse.class);
                        listener.onSuccess(response);

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
                    if (error == null || error.getError() == null) {

                        Gson gson = new Gson();
                        AuthenticationResponse response = gson.fromJson(msg, AuthenticationResponse.class);
                        listener.onSuccess(response);

                    } else {
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
    public void getResponseSocail(SocialLoginRequest signUpRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().loginUsingSocial(new ResponseResolver<AuthenticationResponse>() {
                @Override
                public void onSuccess(AuthenticationResponse signUpResponse, Response response) {
                    listener.onSuccess(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AuthenticationResponse response= gson.fromJson(msg,AuthenticationResponse.class);
                        listener.onSuccess(response);

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
    public void createGuestUser(GenRequest categoryRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().createGuestUser(new ResponseResolver<GuestUserCreateResponse>() {
                @Override
                public void onSuccess(GuestUserCreateResponse signUpResponse, Response response) {
                    listener.onSuccessCreateGuestuser(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        GuestUserCreateResponse response= gson.fromJson(msg,GuestUserCreateResponse.class);
                        listener.onSuccessCreateGuestuser(response);

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
