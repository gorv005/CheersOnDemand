package com.cheersondemand.presenter.coupon;

import android.content.Context;

import com.cheersondemand.intractor.coupon.CouponViewIntractorImpl;
import com.cheersondemand.intractor.coupon.ICouponIntractor;
import com.cheersondemand.model.coupon.ApplyCouponRequest;
import com.cheersondemand.model.coupon.CouponInfoResponse;
import com.cheersondemand.model.coupon.CouponListResponse;
import com.cheersondemand.model.coupon.CouponRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;


public class CouponViewPresenterImpl implements ICouponViewPresenter, ICouponIntractor.OnLoginFinishedListener {

    ICouponView mView;
    Context context;
    ICouponIntractor iCouponIntractor;

    public CouponViewPresenterImpl(ICouponView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iCouponIntractor = new CouponViewIntractorImpl();

    }

    @Override
    public void onSuccessCartAfterCoupon(UpdateCartResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.onSuccessCartAfterCoupon(response);
        }
    }

    @Override
    public void onSuccessCouponList(CouponListResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.onSuccessCouponList(response);
        }
    }

    @Override
    public void onSuccessCouponInfo(CouponInfoResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.onSuccessCouponInfo(response);
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
    public void applyCoupon(boolean isAuthUser, String token, ApplyCouponRequest applyCouponRequest) {
        if (mView != null) {

            iCouponIntractor.applyCoupon(isAuthUser,token,applyCouponRequest, this);
        }
    }

    @Override
    public void getListOfCoupons(boolean isAuthUser, String token, String uuid, String cart_id) {
        if (mView != null) {

            iCouponIntractor.getListOfCoupons(isAuthUser,token,uuid,cart_id, this);
        }
    }

    @Override
    public void getCouponDetails(boolean isAuthUser, String token, String coupon_id, String uuid) {
        if (mView != null) {

            iCouponIntractor.getCouponDetails(isAuthUser,token,coupon_id,uuid, this);
        }
    }

    @Override
    public void removeCoupon(boolean isAuthUser, String token, CouponRequest couponRequest) {
        if (mView != null) {

            iCouponIntractor.removeCoupon(isAuthUser,token,couponRequest, this);
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
