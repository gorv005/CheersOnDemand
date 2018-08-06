package com.cheersondemand.intractor.productDescription;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.productdescription.SimilarProductsResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class ProductDescriptionViewIntractorImpl implements IProductDescIntractor {



    @Override
    public void getSimilarProducts(String token, String productsId, String uuid, String page, String per_page,final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getSimilarProducts(new ResponseResolver<SimilarProductsResponse>() {
                @Override
                public void onSuccess(SimilarProductsResponse r, Response response) {
                    listener.onSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                        Gson gson=new Gson();
                        SimilarProductsResponse response= gson.fromJson(msg,SimilarProductsResponse.class);
                        listener.onSuccess(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,productsId,uuid,page,per_page);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getSimilarProducts(String productsId, String uuid, String page, String per_page, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getSimilarProducts(new ResponseResolver<SimilarProductsResponse>() {
                @Override
                public void onSuccess(SimilarProductsResponse r, Response response) {
                    listener.onSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                        Gson gson=new Gson();
                        SimilarProductsResponse response= gson.fromJson(msg,SimilarProductsResponse.class);
                        listener.onSuccess(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },productsId,uuid,page,per_page);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
