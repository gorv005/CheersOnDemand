package com.cheersondemand.presenter.profile;

import android.content.Context;

import com.cheersondemand.intractor.profile.IProfileViewIntractor;
import com.cheersondemand.intractor.profile.ProfileViewIntractorImpl;
import com.cheersondemand.model.logout.LogoutRequest;
import com.cheersondemand.model.logout.LogoutResponse;


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
    public void onSuccess(LogoutResponse categoriesResponse) {
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
