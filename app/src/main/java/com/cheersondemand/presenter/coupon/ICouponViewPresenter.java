package com.cheersondemand.presenter.coupon;

import com.cheersondemand.model.coupon.CouponInfoResponse;
import com.cheersondemand.model.coupon.CouponListResponse;
import com.cheersondemand.model.coupon.CouponRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;

/**
 * Created by GAURAV on 5/30/2018.
 */

public interface ICouponViewPresenter {

    public void applyCoupon(boolean isAuthUser,String token, String uuid,String code, String cart_value,String cart_id);

    public void getListOfCoupons(boolean isAuthUser,String token, String uuid,String cart_id);

    public void getCouponDetails(boolean isAuthUser,String token, String coupon_id, String uuid);

    public void removeCoupon(boolean isAuthUser, String token, CouponRequest couponRequest);



    void onDestroy();

    interface ICouponView {
        public void onSuccessCouponInfo(CouponInfoResponse response);
        public void onSuccessCouponList(CouponListResponse response);
        public void onSuccessCartAfterCoupon(UpdateCartResponse response);


        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }


}
