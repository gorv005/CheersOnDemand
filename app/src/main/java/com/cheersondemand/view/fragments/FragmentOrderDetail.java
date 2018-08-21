package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.order.OrderHistory;
import com.cheersondemand.model.order.addtocart.Order;
import com.cheersondemand.model.order.addtocart.OrderItem;
import com.cheersondemand.model.order.orderdetail.OrderListResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.presenter.home.order.IOrderDetailViewPresenter;
import com.cheersondemand.presenter.home.order.OrderDetailViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.orderList.AdapterOrderItems;
import com.cheersondemand.view.adapter.orderList.AdapterOrderStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrderDetail extends Fragment implements View.OnClickListener, IOrderDetailViewPresenter.IOrderDetailView {


    @BindView(R.id.tvOrderNo)
    TextView tvOrderNo;
    @BindView(R.id.llOrderNo)
    LinearLayout llOrderNo;
    @BindView(R.id.tvOrderDate)
    TextView tvOrderDate;
    @BindView(R.id.ivProductImage)
    ImageView ivProductImage;
    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.tvQuantity)
    TextView tvQuantity;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvMoreProducts)
    TextView tvMoreProducts;
    @BindView(R.id.rlItem)
    RelativeLayout rlItem;

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.rlAddName)
    RelativeLayout rlAddName;
    @BindView(R.id.tvSubAddress1)
    TextView tvSubAddress1;
    @BindView(R.id.tvSubAddress2)
    TextView tvSubAddress2;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.add)
    RelativeLayout add;
    Unbinder unbinder;
    Util util;
    Order order;
    IOrderDetailViewPresenter iOrderDetailViewPresenter;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    @BindView(R.id.rvOrderStatus)
    RecyclerView rvOrderStatus;
    @BindView(R.id.llTrackOrder)
    LinearLayout llTrackOrder;
    private LinearLayoutManager mLinearLayoutManager;
    AdapterOrderStatus adapterOrderStatus;

    public FragmentOrderDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iOrderDetailViewPresenter = new OrderDetailViewPresenterImpl(this, getActivity());
        util = new Util();
        order = (Order) getArguments().getSerializable(C.ORDER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(getString(R.string.order_detail));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlView.setVisibility(View.GONE);
        tvMoreProducts.setOnClickListener(this);
        llTrackOrder.setOnClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvOrderStatus.setLayoutManager(mLinearLayoutManager);
        rvOrderStatus.setHasFixedSize(true);
        rvOrderStatus.setNestedScrollingEnabled(false);

        getCartList();
    }

    void getCartList() {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iOrderDetailViewPresenter.getCartList(token, id, "" + order.getId(), Util.id(getActivity()), false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessOrderList(OrderListResponse response) {

    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {
        try {
            if (response.getSuccess()) {

                if(response.getData()!=null && response.getData().getOrder()!=null && response.getData().getOrder().getTrackingUrl()!=null){
                    if(response.getData().getOrder().getStatus().equals(C.confirmed)||response.getData().getOrder().getStatus().equals(C.in_transmit)
                    ||response.getData().getOrder().getStatus().equals(C.refunded))
                    {
                        llTrackOrder.setVisibility(View.VISIBLE);
                    }
                    else {
                        llTrackOrder.setVisibility(View.GONE);
                    }

                }
                else {
                    llTrackOrder.setVisibility(View.GONE);
                }
                rlView.setVisibility(View.VISIBLE);

                order = response.getData().getOrder();
                tvOrderNo.setText(order.getOrderNumber());
                tvOrderDate.setText(order.getOrderDate());
                if (order.getOrderItems() != null && order.getOrderItems().size() > 1) {
                    tvMoreProducts.setText("+" + (order.getOrderItems().size() - 1) + " " + getString(R.string.more_products));
                } else {
                    tvMoreProducts.setText("");
                }
                if (order.getOrderItems() != null && order.getOrderItems().size() > 0) {
                    tvProductName.setText(order.getOrderItems().get(0).getProductName());
                    tvQuantity.setText(getString(R.string.quantity) + ": " + order.getOrderItems().get(0).getQuantity());
                    tvPrice.setText("" + getString(R.string.doller) + order.getOrderItems().get(0).getTotalPrice());
                    Util.setImage(getActivity(), order.getOrderItems().get(0).getProduct().getImage(), ivProductImage);

                } else {
                    tvQuantity.setText("");
                    tvPrice.setText("");
                    tvName.setText("");

                }
                if (order.getDeliveryAddress() != null) {
                    tvName.setText(order.getDeliveryAddress().getName());
                    tvSubAddress1.setText(order.getDeliveryAddress().getFlatNo() + " " + order.getDeliveryAddress().getAddressFirst());
                    tvSubAddress2.setText(order.getDeliveryAddress().getAddressSecond() + " " + order.getDeliveryAddress().getZipCode());
                    tvPhone.setText(" " + order.getDeliveryAddress().getPhoneNumber());
                }
                List<OrderHistory> historyList = new ArrayList<>();
                for (int i = 0; i < order.getOrderHistory().size(); i++) {
                    if (order.getOrderHistory().get(i).getOrderDate() != null) {
                        historyList.add(order.getOrderHistory().get(i));
                    }
                }
                adapterOrderStatus = new AdapterOrderStatus(historyList, getActivity());
                rvOrderStatus.setAdapter(adapterOrderStatus);
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessReorderList(OrderListResponse Response) {

    }

    @Override
    public void onSuccessCancelOrder(OrderListResponse Response) {

    }

    void dialog(List<OrderItem> orderItem) {
        LinearLayoutManager layoutManager;

        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.more_order_dialog); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


//to set the message

        TextView title = (TextView) dialog.findViewById(R.id.titleOrders);
        RecyclerView rvOrders = (RecyclerView) dialog.findViewById(R.id.rvOrders);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvOrders.setLayoutManager(layoutManager);
        rvOrders.setHasFixedSize(true);
        title.setText((orderItem.size() - 1) + " " + getActivity().getString(R.string.more_products));
        ImageView cross = (ImageView) dialog.findViewById(R.id.ivCross);
        AdapterOrderItems adapterOrderItems = new AdapterOrderItems(orderItem, getActivity());
        rvOrders.setAdapter(adapterOrderItems);

        cross.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

            }
        });

     /*   Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        int dialogHeight=lp.height;
        lp.height=200;

        if(dialogHeight > 200) {
            lp.height=200;
            dialog.getWindow().setAttributes(lp);
        }*/
        dialog.show();
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlView, true);

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMoreProducts:
                dialog(order.getOrderItems());
                break;
            case R.id.llTrackOrder:
                if(order!=null && order.getTrackingUrl()!=null) {
                    trackOrder(order.getTrackingUrl());
                }
                break;
        }
    }

    void trackOrder(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
