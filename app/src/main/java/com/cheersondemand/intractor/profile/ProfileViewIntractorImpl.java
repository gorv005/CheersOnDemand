package com.cheersondemand.intractor.profile;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.logout.LogoutRequest;
import com.cheersondemand.model.logout.LogoutResponse;
import com.google.gson.Gson;

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

                        Gson gson=new Gson();
                        LogoutResponse response= gson.fromJson(msg,LogoutResponse.class);
                        listener.onSuccess(response);

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
}
