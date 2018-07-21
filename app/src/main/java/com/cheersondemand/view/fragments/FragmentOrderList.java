package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.order.addtocart.Order;
import com.cheersondemand.model.order.orderdetail.CancelOrderRequest;
import com.cheersondemand.model.order.orderdetail.OrderListResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.productdescription.Offers;
import com.cheersondemand.presenter.home.order.IOrderDetailViewPresenter;
import com.cheersondemand.presenter.home.order.OrderDetailViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.orderList.AdapterCancelOrderReason;
import com.cheersondemand.view.adapter.orderList.AdapterOrderList;

import java.util.ArrayList;
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

  public void cancelOrder(String reason,String orderId) {
      CancelOrderRequest cancelOrderRequest=new CancelOrderRequest();
      cancelOrderRequest.setCancellationReason(reason);
      cancelOrderRequest.setUuid(Util.id(getActivity()));
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iOrderDetailViewPresenter.cancelOrder(token, id,""+orderId,cancelOrderRequest);
    }
    public void reOrder(Order order) {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iOrderDetailViewPresenter.reorderOrder(token, id,""+order.getId());
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
    public void getCartListSuccess(UpdateCartResponse response) {

    }

    @Override
    public void onSuccessReorderList(OrderListResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, false);
          //  SharedPreference.getInstance(getActivity()).setBoolean(C.IS_REORDER,true);
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 10 seconds
                    getActivity().finish();
                }
            }, 2000);
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

        }
    }

    @Override
    public void onSuccessCancelOrder(OrderListResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, false);
            //  SharedPreference.getInstance(getActivity()).setBoolean(C.IS_REORDER,true);
            getOrderList();


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
    public void dialogCancelOrder(final Order order) {
        LinearLayoutManager layoutManager;

        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.cancel_order_dialog); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message

        TextView title = (TextView) dialog.findViewById(R.id.titleOrders);
        Button btnCancelOrder = (Button) dialog.findViewById(R.id.btnCancelOrder);
        final EditText etOtherReason = (EditText) dialog.findViewById(R.id.etOtherReason);

        RecyclerView rvOrders=(RecyclerView)dialog.findViewById(R.id.rvCancelReason) ;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvOrders.setLayoutManager(layoutManager);
        rvOrders.setHasFixedSize(true);
        ImageView cross = (ImageView) dialog.findViewById(R.id.ivCross);
        final AdapterCancelOrderReason adapterCancelOrderReason=new AdapterCancelOrderReason(cancelList(),getActivity());
        rvOrders.setAdapter(adapterCancelOrderReason);

        cross.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adapterCancelOrderReason.getSelectedId()!=-1) {
                    cancelOrder(cancelList().get(adapterCancelOrderReason.getSelectedId()).getName(),""+order.getId());
                    dialog.dismiss();
                }
                else if(etOtherReason.getText().toString().length()>0){
                    cancelOrder(etOtherReason.getText().toString(),""+order.getId());
                    dialog.dismiss();

                }
                else {
                    util.setSnackbarMessage(getActivity(), getString(R.string.please_select_one_option), LLView, true);

                }

            }
        });
        dialog.show();
    }
    List<Offers> cancelList() {
        List<Offers> offersList = new ArrayList<>();
        offersList.add(new Offers(1, getString(R.string.item_goes_dammaged), ""));
        offersList.add(new Offers(2, getString(R.string.change_my_mind), ""));
        offersList.add(new Offers(3, getString(R.string.will_bundle), ""));

        return offersList;
    }

}
