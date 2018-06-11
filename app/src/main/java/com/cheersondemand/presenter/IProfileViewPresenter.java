package com.cheersondemand.presenter;

import com.cheersondemand.model.LogoutRequest;

/**
 * Created by GAURAV on 5/30/2018.
 */

public interface IProfileViewPresenter {

    public void logout(LogoutRequest logoutRequest);


    void onDestroy();

    interface IProfileView {
        public void getResponseSuccess(String response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
