package com.cheersondemand.intractor.password;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.changepassword.PasswordRequest;
import com.cheersondemand.model.changepassword.PasswordResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by GAURAV on 7/5/2018.
 */

public class PasswordViewIntractorImpl implements IPasswordViewIntractor {
    @Override
    public void changePassword(String token,String userId, PasswordRequest passwordRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().changePassword(new ResponseResolver<PasswordResponse>() {
                @Override
                public void onSuccess(PasswordResponse r, Response response) {
                    listener.onPasswordRequestSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        PasswordResponse response= gson.fromJson(msg,PasswordResponse.class);
                        listener.onPasswordRequestSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId,passwordRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void forgotPassword(PasswordRequest passwordRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().forgotPassword(new ResponseResolver<PasswordResponse>() {
                @Override
                public void onSuccess(PasswordResponse r, Response response) {
                    listener.onPasswordRequestSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        PasswordResponse response= gson.fromJson(msg,PasswordResponse.class);
                        listener.onPasswordRequestSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },passwordRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
