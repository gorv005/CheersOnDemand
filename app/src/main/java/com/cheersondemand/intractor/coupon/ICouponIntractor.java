package com.cheersondemand.intractor.coupon;

import android.content.Context;

import com.cheersondemand.model.coupon.ApplyCouponRequest;
import com.cheersondemand.model.coupon.CouponInfoResponse;
import com.cheersondemand.model.coupon.CouponListResponse;
import com.cheersondemand.model.coupon.CouponRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;


/**
 * Created by AB on 7/31/2017.
 */

public interface ICouponIntractor {
    interface OnLoginFinishedListener {
        void onSuccessCartAfterCoupon(UpdateCartResponse response);
        void onSuccessCouponList(CouponListResponse response);
        void onSuccessCouponInfo(CouponInfoResponse response);

        void onError(String response);
        Context getAPPContext();
    }
    public void applyCoupon(boolean isAuthUser, String token, ApplyCouponRequest applyCouponRequest, OnLoginFinishedListener listener);

    public void getListOfCoupons(boolean isAuthUser,String token, String uuid,String cart_id, OnLoginFinishedListener listener);

    public void getCouponDetails(boolean isAuthUser,String token, String coupon_id, String uuid, OnLoginFinishedListener listener);

    public void removeCoupon(boolean isAuthUser,String token, CouponRequest couponRequest, OnLoginFinishedListener listener);


}
