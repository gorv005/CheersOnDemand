package com.cheersondemand.presenter.location;

import com.cheersondemand.model.location.RecentLocationResponse;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface ILocationViewPresenter {

    public void saveLocation(SaveLocation saveLocation,String id);

    public void saveLocation(String token,SaveLocation saveLocation,String id);
    public void getRecentLocation(boolean isAuth,String token,String uuid, String user_id);

    void onDestroy();

    interface ILocationView {
        public void getSaveLocationSuccess(SaveLocationResponse response);
        public void getResponseError(String response);
       public void onRecentLocationSuccess(RecentLocationResponse response);

        void showProgress();

        void hideProgress();
    }

}
