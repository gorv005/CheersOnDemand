package com.cheersondemand.presenter.profile;


import com.cheersondemand.model.logout.LogoutRequest;
import com.cheersondemand.model.logout.LogoutResponse;

/**
 * Created by GAURAV on 5/30/2018.
 */

public interface IProfileViewPresenter {

    public void logout(LogoutRequest logoutRequest);


    void onDestroy();

    interface IProfileView {
        public void getResponseSuccess(LogoutResponse response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
