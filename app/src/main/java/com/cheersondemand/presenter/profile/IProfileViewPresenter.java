package com.cheersondemand.presenter.profile;


import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.logout.LogoutRequest;
import com.cheersondemand.model.logout.LogoutResponse;
import com.cheersondemand.model.profile.ProfileUpdateRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by AB on 5/30/2018.
 */

public interface IProfileViewPresenter {

    public void logout(LogoutRequest logoutRequest);
    public void updateProfile(String token, String UserId, MultipartBody.Part part, RequestBody name, RequestBody phone);
    public void updateProfile(String token, String userId, ProfileUpdateRequest profileUpdateRequest);


    void onDestroy();

    interface IProfileView {
        public void getResponseSuccess(LogoutResponse response);
        void onSuccessUpdateProfile(GuestUserCreateResponse Response);

        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
