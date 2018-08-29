package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityHome;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPaymentResult extends Fragment implements View.OnClickListener{

    String paymentResult;
    @BindView(R.id.btnGotoMyOrders)
    Button btnGotoMyOrders;
    @BindView(R.id.llSuccessPayment)
    LinearLayout llSuccessPayment;
    @BindView(R.id.btnRetryPayment)
    Button btnRetryPayment;
    @BindView(R.id.btnGotoMyOrdersFailed)
    Button btnGotoMyOrdersFailed;
    @BindView(R.id.llFailedPayment)
    LinearLayout llFailedPayment;
    @BindView(R.id.btnGotoHome)
    Button btnGotoHome;
    @BindView(R.id.llCancelPayment)
    LinearLayout llCancelPayment;
    @BindView(R.id.LLView)
    RelativeLayout LLView;
    Unbinder unbinder;

    public FragmentPaymentResult() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentResult = getArguments().getString(C.PAYMENT_RESULT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_result, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGotoHome.setOnClickListener(this);
        btnGotoMyOrders.setOnClickListener(this);
        btnGotoMyOrdersFailed.setOnClickListener(this);
        btnRetryPayment.setOnClickListener(this);
        ((ActivityContainer)getActivity()).showToolBar();
        initView();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    void  initView(){
        if (paymentResult.equalsIgnoreCase(C.PAYMENT_SUCCESS)) {
            llCancelPayment.setVisibility(View.GONE);
            llFailedPayment.setVisibility(View.GONE);
            llSuccessPayment.setVisibility(View.VISIBLE);
            ((ActivityContainer)getActivity()).setTitle(getString(R.string.order_confirm));

        } else if (paymentResult.equalsIgnoreCase(C.PAYMENT_FAILED)) {
            llCancelPayment.setVisibility(View.GONE);
            llFailedPayment.setVisibility(View.VISIBLE);
            llSuccessPayment.setVisibility(View.GONE);
            ((ActivityContainer)getActivity()).setTitle(getString(R.string.transaction_failed));


        } else if (paymentResult.equalsIgnoreCase(C.ORDER_CANCEL)) {
            llCancelPayment.setVisibility(View.VISIBLE);
            llFailedPayment.setVisibility(View.GONE);
            llSuccessPayment.setVisibility(View.GONE);
            ((ActivityContainer)getActivity()).setTitle(getString(R.string.cancel_orders));


        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGotoMyOrders:
                ((ActivityContainer)getActivity()).fragmnetLoader(C.FRAGMENT_ORDER_LIST,null);
                break;
            case R.id.btnRetryPayment:
                getActivity().onBackPressed();
                break;
            case R.id.btnGotoMyOrdersFailed:
                ((ActivityContainer)getActivity()).fragmnetLoader(C.FRAGMENT_ORDER_LIST,null);
                break;
            case R.id.btnGotoHome:
               gotoHome();
                break;
        }
    }

    public void gotoHome(){
        Intent intent = new Intent(getActivity(), ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(intent);
    }
}
