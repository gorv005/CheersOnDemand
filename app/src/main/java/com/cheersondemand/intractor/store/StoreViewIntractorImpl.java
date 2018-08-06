package com.cheersondemand.intractor.store;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.store.StoreListResponse;
import com.cheersondemand.model.store.UpdateStore;
import com.cheersondemand.model.store.UpdateStoreResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
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
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            StoreListResponse response = gson.fromJson(msg, StoreListResponse.class);
                            listener.onStoreListSuccess(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getStoreList(String token, String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getStoreList(new ResponseResolver<StoreListResponse>() {
                @Override
                public void onSuccess(StoreListResponse r, Response response) {
                    listener.onStoreListSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            StoreListResponse response = gson.fromJson(msg, StoreListResponse.class);
                            listener.onStoreListSuccess(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, uuid);
        } catch (Exception e) {
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
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            UpdateStoreResponse response = gson.fromJson(msg, UpdateStoreResponse.class);
                            listener.onUpdateStoreSuccess(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, updateStore, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStore(String token, String id, UpdateStore updateStore, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().updateStore(new ResponseResolver<UpdateStoreResponse>() {
                @Override
                public void onSuccess(UpdateStoreResponse r, Response response) {
                    listener.onUpdateStoreSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            UpdateStoreResponse response = gson.fromJson(msg, UpdateStoreResponse.class);
                            listener.onUpdateStoreSuccess(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, updateStore, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
