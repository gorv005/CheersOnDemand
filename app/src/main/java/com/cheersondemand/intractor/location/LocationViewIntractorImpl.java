package com.cheersondemand.intractor.location;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.location.RecentLocationResponse;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class LocationViewIntractorImpl implements ILocationViewIntractor {

    @Override
    public void saveLocation(String id, SaveLocation saveLocation, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().saveLocation(new ResponseResolver<SaveLocationResponse>() {
                @Override
                public void onSuccess(SaveLocationResponse r, Response response) {
                    listener.onLocationSavedSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        SaveLocationResponse response= gson.fromJson(msg,SaveLocationResponse.class);
                        listener.onLocationSavedSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },saveLocation,id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveLocation(String token, String id, SaveLocation saveLocation, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().saveLocation(new ResponseResolver<SaveLocationResponse>() {
                @Override
                public void onSuccess(SaveLocationResponse r, Response response) {
                    listener.onLocationSavedSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        msg=msg.replace("[]","null");
                        msg=msg.replace("{}","null");

                        Gson gson=new Gson();
                        SaveLocationResponse response= gson.fromJson(msg,SaveLocationResponse.class);
                        listener.onLocationSavedSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,saveLocation,id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getRecentLocation(boolean isAuth, String token, String uuid, String user_id, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getRecentLocations(new ResponseResolver<RecentLocationResponse>() {
                @Override
                public void onSuccess(RecentLocationResponse r, Response response) {
                    listener.onRecentLocationSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        msg=msg.replace("[]","null");
                        msg=msg.replace("{}","null");

                        Gson gson=new Gson();
                        RecentLocationResponse response= gson.fromJson(msg,RecentLocationResponse.class);
                        listener.onRecentLocationSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuth,token,uuid,user_id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
