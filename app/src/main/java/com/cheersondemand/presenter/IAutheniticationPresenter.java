package com.cheersondemand.presenter;


import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.model.authentication.CategoryRequest;
import com.cheersondemand.model.authentication.LoginRequest;
import com.cheersondemand.model.authentication.SignUpRequest;
import com.cheersondemand.model.authentication.SocialLoginRequest;

/**
 * Created by GAURAV on 5/30/2018.
 */

public interface IAutheniticationPresenter {
   /* interface IAuthenticationView extends IActivityView {
        void signUp(SignUpRequest signUpRequest);
        void signUpName(String signUpRequest);

        String getName();
        String getPassword();
    }
    interface IAuthenticationPresenter extends IPresenter<IAuthenticationView> {
    }*/

    public void setSignUp(SignUpRequest signUpRequest);
    public void setLoginUsingEmail(LoginRequest loginRequest);

    public void setSignUpSocail(SocialLoginRequest signUpSocail);
    public void createGuestUser(CategoryRequest categoryRequest);

    void onDestroy();

    interface IAuthenticationView {
        public void getResponseSuccess(AuthenticationResponse response);
        public void getResponseSuccessOfCreateGuestUser(GuestUserCreateResponse response);

        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
