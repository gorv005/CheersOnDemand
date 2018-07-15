package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.order.addtocart.Order;
import com.cheersondemand.model.order.orderdetail.OrderListResponse;
import com.cheersondemand.presenter.home.order.IOrderDetailViewPresenter;
import com.cheersondemand.presenter.home.order.OrderDetailViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.orderList.AdapterOrderList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrderList extends Fragment implements IOrderDetailViewPresenter.IOrderDetailView {


    @BindView(R.id.rvOrders)
    RecyclerView rvOrders;
    @BindView(R.id.LLView)
    RelativeLayout LLView;
    Unbinder unbinder;
    LinearLayoutManager layoutManager;
    IOrderDetailViewPresenter iOrderDetailViewPresenter;
    Util util;
    List<Order> orders;
    AdapterOrderList adapterOrderList;
    public FragmentOrderList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iOrderDetailViewPresenter=new OrderDetailViewPresenterImpl(this,getActivity());
        util=new Util();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(getString(R.string.my_orders));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvOrders.setLayoutManager(layoutManager);
        rvOrders.setHasFixedSize(true);
        getOrderList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void getOrderList() {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iOrderDetailViewPresenter.getOrderList(token, id);
    }

    @Override
    public void onSuccessOrderList(OrderListResponse response) {
        if (response.getSuccess()) {
            orders = response.getData().getOrder();

            if (orders!=null && orders.size() > 0) {

                adapterOrderList = new AdapterOrderList(orders, getActivity());
                rvOrders.setAdapter(adapterOrderList);
            }

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

        }
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG,getActivity());

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }


}
