package com.cheersondemand.intractor;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.LogoutRequest;

import retrofit2.Response;

/**
 * Created by GAURAV on 7/31/2017.
 */

public class ProfileViewIntractorImpl implements IProfileViewIntractor {


    @Override
    public void logout(LogoutRequest logoutRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().logout(new ResponseResolver<String>() {
                @Override
                public void onSuccess(String r, Response response) {
                    listener.onSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error!=null && error.getError()!=null) {
                        if(error.getError()==null){
                            listener.onError(error.getMessage());

                        }
                        else {
                            listener.onError(error.getError());
                        }                    }
                }
            },logoutRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
