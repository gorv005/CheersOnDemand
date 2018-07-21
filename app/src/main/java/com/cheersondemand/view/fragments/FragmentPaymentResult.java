package com.cheersondemand.view.fragments;


import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPaymentResult extends Fragment {

    boolean paymentResult;
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
        paymentResult = getArguments().getBoolean(C.PAYMENT_RESULT);

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
