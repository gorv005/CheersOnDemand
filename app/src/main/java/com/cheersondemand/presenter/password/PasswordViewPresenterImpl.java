package com.cheersondemand.presenter.password;

import android.content.Context;

import com.cheersondemand.intractor.password.IPasswordViewIntractor;
import com.cheersondemand.intractor.password.PasswordViewIntractorImpl;
import com.cheersondemand.model.changepassword.PasswordRequest;
import com.cheersondemand.model.changepassword.PasswordResponse;


public class PasswordViewPresenterImpl implements IPasswordViewPresenter, IPasswordViewIntractor.OnLoginFinishedListener {

    IPasswordView mView;
    Context context;
    IPasswordViewIntractor iPasswordViewIntractor;

    public PasswordViewPresenterImpl(IPasswordView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iPasswordViewIntractor = new PasswordViewIntractorImpl();

    }

    @Override
    public void onPasswordRequestSuccess(PasswordResponse Response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getPasswordSuccess(Response);
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
    public void changePassword(String token,String userId, PasswordRequest passwordRequest) {
        if (mView != null) {

            iPasswordViewIntractor.changePassword(token,userId,passwordRequest, this);
        }
    }

    @Override
    public void forgotPassword(PasswordRequest passwordRequest) {
        if (mView != null) {

            iPasswordViewIntractor.forgotPassword(passwordRequest, this);
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
