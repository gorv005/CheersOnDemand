package com.cheersondemand.intractor.home;

import android.util.Log;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SubCategoryResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class HomeViewIntractorImpl implements IHomeViewIntractor {


    @Override
    public void getCategories(String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCategories(new ResponseResolver<BrandResponse>() {
                @Override
                public void onSuccess(BrandResponse signUpResponse, Response response) {
                    listener.onSuccessBrand(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            BrandResponse response = gson.fromJson(msg, BrandResponse.class);
                            listener.onSuccessBrand(response);
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
    public void getCategories(boolean isAuth, String token, String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCategories(new ResponseResolver<CategoriesResponse>() {
                @Override
                public void onSuccess(CategoriesResponse signUpResponse, Response response) {
                    listener.onSuccess(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            CategoriesResponse response = gson.fromJson(msg, CategoriesResponse.class);
                            listener.onSuccess(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, isAuth, token, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSubCategories(boolean isAuth, String token, List<Integer> id, String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getSubCategories(new ResponseResolver<SubCategoryResponse>() {
                @Override
                public void onSuccess(SubCategoryResponse signUpResponse, Response response) {
                    listener.onSuccessSubCat(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            SubCategoryResponse response = gson.fromJson(msg, SubCategoryResponse.class);
                            listener.onSuccessSubCat(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, isAuth, token, id, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBrands(boolean isAuth, String auth, String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getBrands(new ResponseResolver<BrandResponse>() {
                @Override
                public void onSuccess(BrandResponse signUpResponse, Response response) {
                    listener.onSuccessBrand(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            BrandResponse response = gson.fromJson(msg, BrandResponse.class);
                            listener.onSuccessBrand(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, isAuth, auth, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getProductsWithCategories(String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getProductsWithCategories(new ResponseResolver<ProductsWithCategoryResponse>() {
                @Override
                public void onSuccess(ProductsWithCategoryResponse productsWithCategoryResponse, Response response) {
                    listener.onProductWithCategorySuccess(productsWithCategoryResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            ProductsWithCategoryResponse response = gson.fromJson(msg, ProductsWithCategoryResponse.class);
                            listener.onProductWithCategorySuccess(response);
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
    public void getProductsWithCategories(String token, String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getProductsWithCategories(new ResponseResolver<ProductsWithCategoryResponse>() {
                @Override
                public void onSuccess(ProductsWithCategoryResponse productsWithCategoryResponse, Response response) {
                    listener.onProductWithCategorySuccess(productsWithCategoryResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    Log.e("dd", "hh");
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            ProductsWithCategoryResponse response = gson.fromJson(msg, ProductsWithCategoryResponse.class);
                            listener.onProductWithCategorySuccess(response);
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
}
