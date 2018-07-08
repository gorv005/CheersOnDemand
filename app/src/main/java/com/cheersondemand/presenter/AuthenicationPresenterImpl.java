package com.cheersondemand.presenter;

import android.content.Context;

import com.cheersondemand.intractor.authentication.AuthniticationIntractorImpl;
import com.cheersondemand.intractor.authentication.IAuthnicationIntractor;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.authentication.LoginRequest;
import com.cheersondemand.model.authentication.SignUpRequest;
import com.cheersondemand.model.authentication.SocialLoginRequest;


/**
 * Created by AB on 5/30/2018.
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
    public void onSuccessCreateGuestuser(GuestUserCreateResponse guestUserCreateResponse) {
        if(mView!=null){
            //mView.hideProgress();
            mView.getResponseSuccessOfCreateGuestUser(guestUserCreateResponse);
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
    public void createGuestUser(GenRequest categoryRequest) {
        if(mView!=null) {

            iAuthnicationIntractor.createGuestUser(categoryRequest, this);
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
