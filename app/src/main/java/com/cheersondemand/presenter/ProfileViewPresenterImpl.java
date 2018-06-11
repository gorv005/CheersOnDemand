package com.cheersondemand.presenter;

import android.content.Context;

import com.cheersondemand.intractor.IProfileViewIntractor;
import com.cheersondemand.intractor.ProfileViewIntractorImpl;
import com.cheersondemand.model.LoginRequest;
import com.cheersondemand.model.LogoutRequest;


public class ProfileViewPresenterImpl implements IProfileViewPresenter, IProfileViewIntractor.OnLoginFinishedListener {

    IProfileView mView;
    Context context;
    IProfileViewIntractor iProfileViewIntractor;

    public ProfileViewPresenterImpl(IProfileView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iProfileViewIntractor = new ProfileViewIntractorImpl();

    }



    @Override
    public void onSuccess(String categoriesResponse) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getResponseSuccess(categoriesResponse);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }



    @Override
    public void logout(LogoutRequest logoutRequest) {
        if (mView != null) {

            iProfileViewIntractor.logout(logoutRequest, this);
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
