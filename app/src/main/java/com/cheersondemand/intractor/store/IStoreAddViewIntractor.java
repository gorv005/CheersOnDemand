package com.cheersondemand.intractor.store;

import android.content.Context;

import com.cheersondemand.model.store.AddStore;
import com.cheersondemand.model.store.UpdateStoreResponse;

/**
 * Created by AB on 7/31/2017.
 */

public interface IStoreAddViewIntractor {
    interface OnFinishedListener {
        void onAddStoreSuccess(UpdateStoreResponse Response);

        void onError(String response);
        Context getAPPContext();
    }
    public void addStore(AddStore addStore, OnFinishedListener listener);



}
