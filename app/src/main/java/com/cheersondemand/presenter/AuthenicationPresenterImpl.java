package com.cheersondemand.presenter;

import android.content.Context;

import com.cheersondemand.intractor.AuthniticationIntractorImpl;
import com.cheersondemand.intractor.IAuthnicationIntractor;
import com.cheersondemand.model.LoginRequest;
import com.cheersondemand.model.SignUpRequest;
import com.cheersondemand.model.AuthenticationResponse;
import com.cheersondemand.model.SocialLoginRequest;

/**
 * Created by GAURAV on 5/30/2018.
 */

public class AuthenicationPresenterImpl implements IAutheniticationPresenter,IAuthnicationIntractor.OnLoginFinishedListener{

    IAutheniticationPresenter.IAuthenticationView mView;
    Context context;
    IAuthnicationIntractor iAuthnicationIntractor;
    public AuthenicationPresenterImpl(IAutheniticationPresenter.IAuthenticationView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iAuthnicationIntractor=new AuthniticationIntractorImpl();

    }

    @Override
    public void onSuccess(AuthenticationResponse signUpResponse) {
        if(mView!=null){
            //mView.hideProgress();
            mView.getResponseSuccess(signUpResponse);
        }
    }

    @Override
    public void onError(String response) {
        if(mView!=null){
            //mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }

    @Override
    public void setSignUp(SignUpRequest signUpRequest) {
        if(mView!=null) {

            iAuthnicationIntractor.getResponse(signUpRequest, this);
        }
    }

    @Override
    public void setLoginUsingEmail(LoginRequest loginUsingEmail) {
        if(mView!=null) {

            iAuthnicationIntractor.loginUsingEmail(loginUsingEmail, this);
        }
    }
    @Override
    public void setSignUpSocail(SocialLoginRequest signUpSocail) {
        if(mView!=null) {

            iAuthnicationIntractor.getResponseSocail(signUpSocail, this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /*@Override
    public void onViewStarted() {
        String n=getView().getName();
        getView().signUpName(n);
        WebServicesWrapper.getInstance().signUp(new ResponseResolver<SignUpResponse>() {
            @Override
            public void onSuccess(SignUpResponse signUpResponse, Response response) {

            }

            @Override
            public void onFailure(RestError error, String msg) {

            }
        },new SignUpRequest());
    }*/





}
