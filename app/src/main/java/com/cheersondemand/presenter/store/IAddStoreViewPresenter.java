package com.cheersondemand.presenter.store;

import com.cheersondemand.model.store.AddStore;
import com.cheersondemand.model.store.UpdateStoreResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface IAddStoreViewPresenter {


    public void addStore(AddStore addStore);

    void onDestroy();

    interface IAddStoreView {
        void onAddStoreSuccess(UpdateStoreResponse Response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
