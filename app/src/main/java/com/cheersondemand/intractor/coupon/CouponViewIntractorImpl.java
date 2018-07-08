package com.cheersondemand.intractor.coupon;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.coupon.ApplyCouponRequest;
import com.cheersondemand.model.coupon.CouponInfoResponse;
import com.cheersondemand.model.coupon.CouponListResponse;
import com.cheersondemand.model.coupon.CouponRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class CouponViewIntractorImpl implements ICouponIntractor {


    @Override
    public void applyCoupon(boolean isAuthUser, String token, ApplyCouponRequest applyCouponRequest, final OnLoginFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().applyCoupon(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse res, Response response) {
                    listener.onSuccessCartAfterCoupon(res);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        UpdateCartResponse response= gson.fromJson(msg,UpdateCartResponse.class);
                        listener.onSuccessCartAfterCoupon(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,applyCouponRequest);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getListOfCoupons(boolean isAuthUser, String token, String uuid, String cart_id, final OnLoginFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().getListOfCoupons(new ResponseResolver<CouponListResponse>() {
                @Override
                public void onSuccess(CouponListResponse res, Response response) {
                    listener.onSuccessCouponList(res);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        CouponListResponse response= gson.fromJson(msg,CouponListResponse.class);
                        listener.onSuccessCouponList(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,uuid,cart_id);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getCouponDetails(boolean isAuthUser, String token, String coupon_id, String uuid,final OnLoginFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().getCouponDetail(new ResponseResolver<CouponInfoResponse>() {
                @Override
                public void onSuccess(CouponInfoResponse res, Response response) {
                    listener.onSuccessCouponInfo(res);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        CouponInfoResponse response= gson.fromJson(msg,CouponInfoResponse.class);
                        listener.onSuccessCouponInfo(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,coupon_id,uuid);
        }

        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void removeCoupon(boolean isAuthUser, String token, CouponRequest couponRequest,final OnLoginFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().removeCoupon(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse res, Response response) {
                    listener.onSuccessCartAfterCoupon(res);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        UpdateCartResponse response= gson.fromJson(msg,UpdateCartResponse.class);
                        listener.onSuccessCartAfterCoupon(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,couponRequest);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }
}
