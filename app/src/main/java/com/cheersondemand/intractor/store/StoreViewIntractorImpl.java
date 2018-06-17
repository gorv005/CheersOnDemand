package com.cheersondemand.intractor.store;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.store.StoreListResponse;
import com.cheersondemand.model.store.UpdateStore;
import com.cheersondemand.model.store.UpdateStoreResponse;

import retrofit2.Response;

/**
 * Created by GAURAV on 7/31/2017.
 */

public class StoreViewIntractorImpl implements IStoreViewIntractor {


    @Override
    public void getStoreList(String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getStoreList(new ResponseResolver<StoreListResponse>() {
                @Override
                public void onSuccess(StoreListResponse r, Response response) {
                    listener.onStoreListSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error!=null && error.getError()!=null) {
                        if(error.getError()==null){
                            listener.onError(error.getMessage());

                        }
                        else {
                            listener.onError(error.getError());
                        }                    }
                }
            },uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateStore(String id, UpdateStore updateStore, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().updateStore(new ResponseResolver<UpdateStoreResponse>() {
                @Override
                public void onSuccess(UpdateStoreResponse r, Response response) {
                    listener.onUpdateStoreSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error!=null && error.getError()!=null) {
                        if(error.getError()==null){
                            listener.onError(error.getMessage());

                        }
                        else {
                            listener.onError(error.getError());
                        }                    }
                }
            },updateStore,id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
