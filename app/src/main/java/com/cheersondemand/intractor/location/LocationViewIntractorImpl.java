package com.cheersondemand.intractor.location;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;

import retrofit2.Response;

/**
 * Created by GAURAV on 7/31/2017.
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
                    if(error!=null && error.getError()!=null) {
                        if(error.getError()==null){
                            listener.onError(error.getMessage());

                        }
                        else {
                            listener.onError(error.getError());
                        }                    }
                }
            },saveLocation,id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
