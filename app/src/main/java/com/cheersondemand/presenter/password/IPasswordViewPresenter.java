package com.cheersondemand.presenter.password;

import com.cheersondemand.model.changepassword.PasswordRequest;
import com.cheersondemand.model.changepassword.PasswordResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface IPasswordViewPresenter {

    public void changePassword(String token,String userId, PasswordRequest passwordRequest);
    public void forgotPassword(PasswordRequest passwordRequest);


    void onDestroy();

    interface IPasswordView {
        public void getPasswordSuccess(PasswordResponse response);

        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
