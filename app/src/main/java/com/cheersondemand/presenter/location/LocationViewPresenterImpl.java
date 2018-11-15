package com.cheersondemand.presenter.location;

import android.content.Context;

import com.cheersondemand.intractor.location.ILocationViewIntractor;
import com.cheersondemand.intractor.location.LocationViewIntractorImpl;
import com.cheersondemand.model.location.RecentLocationResponse;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;


public class LocationViewPresenterImpl implements ILocationViewPresenter, ILocationViewIntractor.OnLoginFinishedListener {

    ILocationView mView;
    Context context;
    ILocationViewIntractor iLocationViewIntractor;

    public LocationViewPresenterImpl(ILocationView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iLocationViewIntractor = new LocationViewIntractorImpl();

    }


    @Override
    public void onLocationSavedSuccess(SaveLocationResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getSaveLocationSuccess(Response);
        }
    }

    @Override
    public void onRecentLocationSuccess(RecentLocationResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onRecentLocationSuccess(response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }






    @Override
    public void saveLocation(SaveLocation saveLocation, String id) {
        if (mView != null) {
            mView.showProgress();
            iLocationViewIntractor.saveLocation(id,saveLocation ,this);
        }
    }

    @Override
    public void saveLocation(String token, SaveLocation saveLocation, String id) {
        if (mView != null) {
            mView.showProgress();
            iLocationViewIntractor.saveLocation(token,id,saveLocation ,this);
        }
    }

    @Override
    public void getRecentLocation(boolean isAuth, String token, String uuid, String user_id) {
        if (mView != null) {
            mView.showProgress();
            iLocationViewIntractor.getRecentLocation(isAuth,token,uuid,user_id ,this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
