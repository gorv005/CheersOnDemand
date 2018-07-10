package com.cheersondemand.presenter.store;

import android.content.Context;

import com.cheersondemand.intractor.store.AddStoreViewIntractorImpl;
import com.cheersondemand.intractor.store.IStoreAddViewIntractor;
import com.cheersondemand.model.store.AddStore;
import com.cheersondemand.model.store.UpdateStoreResponse;


public class AddStoreViewPresenterImpl implements IAddStoreViewPresenter, IStoreAddViewIntractor.OnFinishedListener {

    IAddStoreView mView;
    Context context;
    IStoreAddViewIntractor iStoreAddViewIntractor;

    public AddStoreViewPresenterImpl(IAddStoreView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iStoreAddViewIntractor = new AddStoreViewIntractorImpl();

    }


    @Override
    public void onAddStoreSuccess(UpdateStoreResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onAddStoreSuccess(Response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }

    @Override
    public void addStore(AddStore addStore) {
        if (mView != null) {
            mView.showProgress();
            iStoreAddViewIntractor.addStore(addStore, this);
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
