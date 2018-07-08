package com.cheersondemand.intractor.location;

import android.content.Context;

import com.cheersondemand.model.location.RecentLocationResponse;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;

/**
 * Created by AB on 7/31/2017.
 */

public interface ILocationViewIntractor {
    interface OnLoginFinishedListener {
        void onLocationSavedSuccess(SaveLocationResponse Response);
        void onRecentLocationSuccess(RecentLocationResponse response);

        void onError(String response);
        Context getAPPContext();
    }
    public void saveLocation(String id, SaveLocation saveLocation, OnLoginFinishedListener listener);
    public void saveLocation(String token,String id, SaveLocation saveLocation, OnLoginFinishedListener listener);
    public void getRecentLocation(boolean isAuth,String token,String uuid, String user_id, OnLoginFinishedListener listener);



}
