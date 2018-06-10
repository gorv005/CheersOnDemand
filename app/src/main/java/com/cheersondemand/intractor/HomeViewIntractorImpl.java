package com.cheersondemand.intractor;

import android.util.Log;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.CategoryRequest;

import retrofit2.Response;

/**
 * Created by GAURAV on 7/31/2017.
 */

public class HomeViewIntractorImpl implements IHomeViewIntractor {



    @Override
    public void getCategories(CategoryRequest uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCategories(new ResponseResolver<CategoriesResponse>() {
                @Override
                public void onSuccess(CategoriesResponse signUpResponse, Response response) {
                    listener.onSuccess(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    Log.e("dd","hh");
                    if(error!=null && error.getError()!=null) {
                        listener.onError(error.getError());
                    }
                }
            },uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
