package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.coupon.ApplyCouponRequest;
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
    LinearLayoutManager layoutManager;
    String cartValue;
    private String couponName="";

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
        cartValue=getArguments().getString(C.CART_VALUE);
        couponName=getArguments().getString(C.COUPON_NAME);

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
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCouponList.setLayoutManager(layoutManager);
        btnApply.setEnabled(false);
        btnApply.setOnClickListener(this);
        init();
        getCouponList();

    }

    void init(){
        etCouponName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==6){
                    btnApply.setEnabled(true);
                    btnApply.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                }
                else {
                    btnApply.setEnabled(false);
                    btnApply.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                }
            }
        });
    }



   public void applyCoupon(String couponName){
        this.couponName=couponName;
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

       ApplyCouponRequest applyCouponRequest=new ApplyCouponRequest();
       applyCouponRequest.setCartId(order_id);
       applyCouponRequest.setUuid(Util.id(getActivity()));
       applyCouponRequest.setCartValue(cartValue);
       applyCouponRequest.setCode(couponName);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iCouponViewPresenter.applyCoupon(false,"",applyCouponRequest);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token =  C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iCouponViewPresenter.applyCoupon(true,token,applyCouponRequest);

        }

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

       switch (v.getId()){
           case R.id.btnApply:
               if(etCouponName.getText().length()>=6) {
                   applyCoupon(etCouponName.getText().toString());
               }
               break;
       }
    }

    @Override
    public void onSuccessCouponInfo(CouponInfoResponse response) {

    }

    @Override
    public void onSuccessCouponList(CouponListResponse response) {
        if(response.getSuccess()){
            couponInfoList=response.getData();
            for(int i=0;i<couponInfoList.size();i++){
                couponInfoList.get(i).setCouponName(couponName);
            }
            adapterCouponList=new AdapterCouponList(couponInfoList,getActivity(),couponName) ;
            if(couponInfoList!=null && couponInfoList.size()>0){
                rvCouponList.setAdapter(adapterCouponList);
            }
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

        }
    }

    @Override
    public void onSuccessCartAfterCoupon(UpdateCartResponse response) {

       if(response.getSuccess()){
           util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, false);

           for(int i=0;i<couponInfoList.size();i++){
               couponInfoList.get(i).setCouponName(couponName);
           }
          //  adapterCouponList.setData(couponInfoList,couponName);
            adapterCouponList.notifyDataSetChanged();
       }
       else {
           util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

       }
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
