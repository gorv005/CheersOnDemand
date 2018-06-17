package com.cheersondemand.presenter.location;

import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;

/**
 * Created by GAURAV on 5/30/2018.
 */

public interface ILocationViewPresenter {

    public void saveLocation(SaveLocation saveLocation,String id);


    void onDestroy();

    interface ILocationView {
        public void getSaveLocationSuccess(SaveLocationResponse response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
