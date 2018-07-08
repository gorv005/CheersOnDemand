package com.cheersondemand.view.fragments;


import android.content.Intent;
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

import com.cheersondemand.R;
import com.cheersondemand.model.address.Address;
import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressResponse;
import com.cheersondemand.presenter.address.AddressViewPresenterImpl;
import com.cheersondemand.presenter.address.IAddressViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.address.AdapterAddress;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddressList extends Fragment implements View.OnClickListener, IAddressViewPresenter.IAddressView {


    @BindView(R.id.rvAddressList)
    RecyclerView rvAddressList;

    @BindView(R.id.rlView)
    RelativeLayout rlView;
    Unbinder unbinder;

    IAddressViewPresenter iAddressViewPresenter;
    List<Address> addresses;
    Util util;
    AdapterAddress adapterAddress;
    LinearLayoutManager layoutManager;
    int position;
    @BindView(R.id.btnAddAnAddress)
    Button btnAddAnAddress;
    @BindView(R.id.llNoProductInCount)
    LinearLayout llNoProductInCount;

    public FragmentAddressList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iAddressViewPresenter = new AddressViewPresenterImpl(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvAddressList.setLayoutManager(layoutManager);
        rvAddressList.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(getString(R.string.saved_addresses));
        getAddressList();
    }


    public void removeAddress(Address address, int pos) {
        position = pos;
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iAddressViewPresenter.RemoveAddAddress(token, id, "" + address.getId());
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
    public void onRemoveAddressSuccess(AddressResponse Response) {
        if (Response.getSuccess()) {
            addresses.remove(position);
            if(addresses.size()==0){
                llNoProductInCount.setVisibility(View.VISIBLE);
                rvAddressList.setVisibility(View.GONE);
            }
            adapterAddress.notifyDataSetChanged();
        } else {
            util.setSnackbarMessage(getActivity(), Response.getMessage(), rlView, true);

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
        if (Response.getSuccess()) {
            if (Response.getData() != null && Response.getData().size() > 0) {
                llNoProductInCount.setVisibility(View.GONE);
                rvAddressList.setVisibility(View.VISIBLE);

                addresses = Response.getData();
                adapterAddress = new AdapterAddress(addresses, getActivity());
                rvAddressList.setAdapter(adapterAddress);
            } else {
                llNoProductInCount.setVisibility(View.VISIBLE);
                rvAddressList.setVisibility(View.GONE);

            }
        }
        else {
            util.setSnackbarMessage(getActivity(), Response.getMessage(), rlView, true);

        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlView, true);

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG,getActivity());

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddAnAddress:
                Intent intent=new Intent(getActivity(), ActivityContainer.class);
                Bundle bundle=new Bundle();
                bundle.putBoolean(C.IS_EDIT,false);
                intent.putExtra(C.BUNDLE,bundle);
                intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_ADD_ADDRESS);
                startActivity(intent);

                break;
        }
    }
}
