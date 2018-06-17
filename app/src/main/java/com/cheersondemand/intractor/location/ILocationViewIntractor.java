package com.cheersondemand.intractor.location;

import android.content.Context;

import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;

/**
 * Created by GAURAV on 7/31/2017.
 */

public interface ILocationViewIntractor {
    interface OnLoginFinishedListener {
        void onLocationSavedSuccess(SaveLocationResponse Response);
        void onError(String response);
        Context getAPPContext();
    }
    public void saveLocation(String id, SaveLocation saveLocation, OnLoginFinishedListener listener);



}
