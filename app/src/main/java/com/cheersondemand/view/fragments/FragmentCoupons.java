package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.coupon.CouponInfo;
import com.cheersondemand.model.coupon.CouponInfoResponse;
import com.cheersondemand.model.coupon.CouponListResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.presenter.coupon.CouponViewPresenterImpl;
import com.cheersondemand.presenter.coupon.ICouponViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.coupon.AdapterCouponList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCoupons extends Fragment implements View.OnClickListener, ICouponViewPresenter.ICouponView {


    @BindView(R.id.etCouponName)
    EditText etCouponName;
    @BindView(R.id.btnApply)
    Button btnApply;
    @BindView(R.id.tvOr)
    TextView tvOr;
    @BindView(R.id.rvCouponList)
    RecyclerView rvCouponList;
    @BindView(R.id.LLView)
    LinearLayout LLView;
    Unbinder unbinder;
       ICouponViewPresenter iCouponViewPresenter;
       Util util;
       AdapterCouponList adapterCouponList;
      List<CouponInfo>  couponInfoList;
    public FragmentCoupons() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(R.string.coupon_code);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iCouponViewPresenter=new CouponViewPresenterImpl(this,getActivity());
        util=new Util();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coupons, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCouponList();
    }



   void getCouponList(){
       String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

       if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
           String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

           iCouponViewPresenter.getListOfCoupons(false,"",Util.id(getActivity()),order_id);
       } else {
           String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
           String token =  C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
           iCouponViewPresenter.getListOfCoupons(true,token,Util.id(getActivity()),order_id);
       }
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccessCouponInfo(CouponInfoResponse response) {

    }

    @Override
    public void onSuccessCouponList(CouponListResponse response) {
        if(response.getSuccess()){
            couponInfoList=response.getData();
            adapterCouponList=new AdapterCouponList(couponInfoList,getActivity()) ;
            if(couponInfoList!=null && couponInfoList.size()>0){
                rvCouponList.setAdapter(adapterCouponList);
            }
        }
    }

    @Override
    public void onSuccessCartAfterCoupon(UpdateCartResponse response) {

    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
