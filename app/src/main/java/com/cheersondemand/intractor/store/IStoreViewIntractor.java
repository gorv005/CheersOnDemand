package com.cheersondemand.intractor.store;

import android.content.Context;

import com.cheersondemand.model.store.StoreListResponse;
import com.cheersondemand.model.store.UpdateStore;
import com.cheersondemand.model.store.UpdateStoreResponse;

/**
 * Created by GAURAV on 7/31/2017.
 */

public interface IStoreViewIntractor {
    interface OnLoginFinishedListener {
        void onStoreListSuccess(StoreListResponse Response);
        void onUpdateStoreSuccess(UpdateStoreResponse Response);

        void onError(String response);
        Context getAPPContext();
    }
    public void getStoreList(String uuid, OnLoginFinishedListener listener);
    public void getStoreList(String token,String uuid, OnLoginFinishedListener listener);

    public void updateStore(String id, UpdateStore updateStore, OnLoginFinishedListener listener);

    public void updateStore(String token,String id, UpdateStore updateStore, OnLoginFinishedListener listener);


}
