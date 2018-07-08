package com.cheersondemand.presenter.location;

import android.content.Context;

import com.cheersondemand.intractor.location.ILocationViewIntractor;
import com.cheersondemand.intractor.location.LocationViewIntractorImpl;
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
    public void onDestroy() {
        try {
            mView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
