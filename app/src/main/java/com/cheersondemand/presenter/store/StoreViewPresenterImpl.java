package com.cheersondemand.presenter.store;

import android.content.Context;

import com.cheersondemand.intractor.store.IStoreViewIntractor;
import com.cheersondemand.intractor.store.StoreViewIntractorImpl;
import com.cheersondemand.model.store.StoreListResponse;
import com.cheersondemand.model.store.UpdateStore;
import com.cheersondemand.model.store.UpdateStoreResponse;


public class StoreViewPresenterImpl implements IStoreViewPresenter, IStoreViewIntractor.OnLoginFinishedListener {

    IStoreView mView;
    Context context;
    IStoreViewIntractor iStoreViewIntractor;

    public StoreViewPresenterImpl(IStoreView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iStoreViewIntractor = new StoreViewIntractorImpl();

    }


    @Override
    public void onStoreListSuccess(StoreListResponse Response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getStoreListSuccess(Response);
        }
    }

    @Override
    public void onUpdateStoreSuccess(UpdateStoreResponse Response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.updateStoreSuccess(Response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }





    @Override
    public void getStoreList(String uuid) {
        if (mView != null) {

            iStoreViewIntractor.getStoreList(uuid, this);
        }
    }

    @Override
    public void getStoreList(String token, String uuid) {
        if (mView != null) {

            iStoreViewIntractor.getStoreList(token,uuid, this);
        }
    }

    @Override
    public void updateStore(String id, UpdateStore updateStore) {
        if (mView != null) {

            iStoreViewIntractor.updateStore(id,updateStore, this);
        }
    }

    @Override
    public void updateStore(String token, String id, UpdateStore updateStore) {
        if (mView != null) {

            iStoreViewIntractor.updateStore(token,id,updateStore, this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
