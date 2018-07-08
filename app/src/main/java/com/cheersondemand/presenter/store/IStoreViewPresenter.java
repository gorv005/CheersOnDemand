package com.cheersondemand.presenter.store;

import com.cheersondemand.model.store.StoreListResponse;
import com.cheersondemand.model.store.UpdateStore;
import com.cheersondemand.model.store.UpdateStoreResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface IStoreViewPresenter {

    public void getStoreList(String uuid);
    public void getStoreList(String token,String uuid);

    public void updateStore(String id, UpdateStore updateStore);
    public void updateStore(String token,String id, UpdateStore updateStore);

    void onDestroy();

    interface IStoreView {
        public void getStoreListSuccess(StoreListResponse response);
        public void updateStoreSuccess(UpdateStoreResponse response);

        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
