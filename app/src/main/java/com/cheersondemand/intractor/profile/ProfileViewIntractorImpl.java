package com.cheersondemand.intractor.profile;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.logout.LogoutRequest;
import com.cheersondemand.model.logout.LogoutResponse;
import com.cheersondemand.model.profile.ProfileUpdateRequest;
import com.google.gson.Gson;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class ProfileViewIntractorImpl implements IProfileViewIntractor {


    @Override
    public void logout(LogoutRequest logoutRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().logout(new ResponseResolver<LogoutResponse>() {
                @Override
                public void onSuccess(LogoutResponse r, Response response) {
                    listener.onSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                        Gson gson=new Gson();
                        LogoutResponse response= gson.fromJson(msg,LogoutResponse.class);
                        listener.onSuccess(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },logoutRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfile(String token, String UserId, MultipartBody.Part part, RequestBody name, RequestBody phone, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().updateProfile(new ResponseResolver<GuestUserCreateResponse>() {
                @Override
                public void onSuccess(GuestUserCreateResponse r, Response response) {
                    listener.onSuccessUpdateProfile(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                        Gson gson=new Gson();
                        GuestUserCreateResponse response= gson.fromJson(msg,GuestUserCreateResponse.class);
                        listener.onSuccessUpdateProfile(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,UserId,part,name,phone);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfile(String token, String userId, ProfileUpdateRequest profileUpdateRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().updateProfile(new ResponseResolver<GuestUserCreateResponse>() {
                @Override
                public void onSuccess(GuestUserCreateResponse r, Response response) {
                    listener.onSuccessUpdateProfile(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                        Gson gson=new Gson();
                        GuestUserCreateResponse response= gson.fromJson(msg,GuestUserCreateResponse.class);
                        listener.onSuccessUpdateProfile(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId,profileUpdateRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
