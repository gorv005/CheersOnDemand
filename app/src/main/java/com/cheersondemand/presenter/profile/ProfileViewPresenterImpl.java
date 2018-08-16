package com.cheersondemand.presenter.profile;

import android.content.Context;

import com.cheersondemand.intractor.profile.IProfileViewIntractor;
import com.cheersondemand.intractor.profile.ProfileViewIntractorImpl;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.logout.LogoutRequest;
import com.cheersondemand.model.logout.LogoutResponse;
import com.cheersondemand.model.profile.ProfileUpdateRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


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
            mView.hideProgress();
            mView.getResponseSuccess(categoriesResponse);
        }
    }

    @Override
    public void onSuccessUpdateProfile(GuestUserCreateResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onSuccessUpdateProfile(Response);
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
    public void logout(LogoutRequest logoutRequest) {
        if (mView != null) {
            mView.showProgress();

            iProfileViewIntractor.logout(logoutRequest, this);
        }

    }

    @Override
    public void updateProfile(String token, String UserId, MultipartBody.Part part, RequestBody name, RequestBody phone) {
        if (mView != null) {
            mView.showProgress();
            iProfileViewIntractor.updateProfile(token,UserId,part,name,phone, this);
        }
    }

    @Override
    public void updateProfile(String token, String userId, ProfileUpdateRequest profileUpdateRequest) {
        if (mView != null) {
            mView.showProgress();
            iProfileViewIntractor.updateProfile(token,userId,profileUpdateRequest, this);
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
