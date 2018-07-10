package com.cheersondemand.intractor.store;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.store.AddStore;
import com.cheersondemand.model.store.UpdateStoreResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class AddStoreViewIntractorImpl implements IStoreAddViewIntractor {


    @Override
    public void addStore(AddStore addStore, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().addStore(new ResponseResolver<UpdateStoreResponse>() {
                @Override
                public void onSuccess(UpdateStoreResponse r, Response response) {
                    listener.onAddStoreSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        UpdateStoreResponse response= gson.fromJson(msg,UpdateStoreResponse.class);
                        listener.onAddStoreSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },addStore);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
