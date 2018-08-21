package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.address.AddDeliveryAddressRequest;
import com.cheersondemand.model.address.Address;
import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressResponse;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.presenter.address.AddressViewPresenterImpl;
import com.cheersondemand.presenter.address.IAddressViewPresenter;
import com.cheersondemand.presenter.card.CardViewPresenterImpl;
import com.cheersondemand.presenter.card.ICardViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.address.AdapterAddressSelection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddressSelection extends Fragment implements View.OnClickListener, IAddressViewPresenter.IAddressView , ICardViewPresenter.ICardView {

    int position;

    @BindView(R.id.tvChooseAddress)
    TextView tvChooseAddress;
    @BindView(R.id.rvAddressList)
    RecyclerView rvAddressList;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    Unbinder unbinder;
    IAddressViewPresenter iAddressViewPresenter;
    ICardViewPresenter iCardViewPresenter;
    List<Address> addresses;
    Util util;
    AdapterAddressSelection adapterAddress;
    LinearLayoutManager layoutManager;
    @BindView(R.id.btnAddAnAddress)
    Button btnAddAnAddress;
    @BindView(R.id.llNoProductInCount)
    LinearLayout llNoProductInCount;
    @BindView(R.id.btnConfirmAdd)
    Button btnConfirmAdd;
    @BindView(R.id.llButton)
    RelativeLayout llButton;
    int Address_id;
    public FragmentAddressSelection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iAddressViewPresenter = new AddressViewPresenterImpl(this, getActivity());
        iCardViewPresenter = new CardViewPresenterImpl(this, getActivity());

        if(getArguments()!=null) {
            Address_id = getArguments().getInt(C.ADDRESS_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_selection, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvAddressList.setLayoutManager(layoutManager);
        rvAddressList.setHasFixedSize(true);
        btnConfirmAdd.setOnClickListener(this);
        btnAddAnAddress.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(getString(R.string.address));
        getAddressList();
    }


    public void addDeliveryAddress(Address address) {
        AddDeliveryAddressRequest addDeliveryAddressRequest=new AddDeliveryAddressRequest();
        addDeliveryAddressRequest.setDeliveryAddressId(""+address.getId());
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
        String order_id = "" + SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iAddressViewPresenter.addDeliveryAddress(token, id, order_id,addDeliveryAddressRequest);
    }
    public void removeAddress(Address address, int pos) {
        position = pos;
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iAddressViewPresenter.RemoveAddAddress(token, id, "" + address.getId());
    }
    public void changeAddress(Address address) {
       Address_id=address.getId();
    }
    void getAddressList() {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iAddressViewPresenter.getAddresses(token, id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRemoveAddressSuccess(AddressAddResponse Response) {
        try {
        if (Response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), Response.getMessage(), rlView, false);

            addresses.remove(position);
            if (addresses.size() == 0) {
                llNoProductInCount.setVisibility(View.VISIBLE);
                rvAddressList.setVisibility(View.GONE);
                llButton.setVisibility(View.GONE);
            }
            else {
                Address_id=addresses.get(0).getId();

                adapterAddress.setPostion(addresses.get(0).getId());
            }
            adapterAddress.notifyDataSetChanged();
        } else {
            util.setSnackbarMessage(getActivity(), Response.getMessage(), rlView, true);

        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAddAddressSuccess(AddressAddResponse Response) {

    }

    @Override
    public void onEditAddressSuccess(AddressAddResponse Response) {

    }

    @Override
    public void onAddressListSuccess(AddressResponse Response) {
        try {
        if (Response.getSuccess()) {
            if (Response.getData() != null && Response.getData().size() > 0) {
                llNoProductInCount.setVisibility(View.GONE);
                rvAddressList.setVisibility(View.VISIBLE);
                llButton.setVisibility(View.VISIBLE);

                addresses = Response.getData();
                adapterAddress = new AdapterAddressSelection(addresses, getActivity(),Address_id);
                rvAddressList.setAdapter(adapterAddress);
            } else {
                llNoProductInCount.setVisibility(View.VISIBLE);
                rvAddressList.setVisibility(View.GONE);
                llButton.setVisibility(View.GONE);
            }
        } else {
            util.setSnackbarMessage(getActivity(), Response.getMessage(), rlView, true);

        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAddDeliveryAddressSuccess(AddressAddResponse Response) {
        try {
        if(Response.getSuccess()){
           // ((ActivityContainer)getActivity()).fragmnetLoader(C.FRAGMENT_PAYMENT_CONFIRMATION,null);
            getCardList();
        }
        else {
            dialog(Response.getMessage());
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void getCardList() {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iCardViewPresenter.getCardList(token, id);
    }
    @Override
    public void onSuccessCardList(CardListResponse response) {
        if (response.getSuccess()) {
            if (response.getData() != null && response.getData().size() > 0) {
                Bundle bundle=new Bundle();
                bundle.putBoolean(C.IS_RETRY_PAYEMNT,false);
                 ((ActivityContainer)getActivity()).fragmnetLoader(C.FRAGMENT_PAYMENT_CONFIRMATION,bundle);


            } else {
                // tvNoCardAvailable.setVisibility(View.VISIBLE);
             gotoCard();

            }
        }
        else {
            // tvNoCardAvailable.setVisibility(View.VISIBLE);

            gotoCard();

        }
    }


    void gotoCard(){
        Bundle bundle3 = new Bundle();

        bundle3.putBoolean(C.IS_FROM_CHECKOUT, true);
        bundle3.putBoolean(C.IS_RETRY_PAYEMNT, false);

        ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_ADD_CARD, bundle3);
    }
    @Override
    public void onSuccessAddCard(CardAddResponse response) {

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
            case R.id.btnAddAnAddress:
                Intent intent = new Intent(getActivity(), ActivityContainer.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(C.IS_EDIT, false);
                bundle.putBoolean(C.IS_FROM_CHECKOUT, true);
                bundle.putBoolean(C.IS_RETRY_PAYEMNT, false);

                intent.putExtra(C.BUNDLE, bundle);
                intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_ADD_ADDRESS);
                startActivity(intent);

                break;
            case R.id.btnConfirmAdd:

                if(adapterAddress!=null){
                   addDeliveryAddress(adapterAddress.getSelectedAddress());
                }
                break;
        }
    }

    void dialog(String msg) {
        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.dialog_ok); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message
        TextView title = (TextView) dialog.findViewById(R.id.tvmessagedialogtitle);

        TextView message = (TextView) dialog.findViewById(R.id.tvmessagedialogtext);
        title.setText(getString(R.string.app_name));
        message.setText(msg);

        Button ok = (Button) dialog.findViewById(R.id.bmessageDialogOK);
        ok.setText(getString(R.string.ok));
        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
