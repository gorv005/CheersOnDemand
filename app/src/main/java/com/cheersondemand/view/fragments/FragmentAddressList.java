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
import com.cheersondemand.model.address.RemoveAddressRequest;
import com.cheersondemand.model.location.RecentLocation;
import com.cheersondemand.model.location.RecentLocationResponse;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;
import com.cheersondemand.presenter.address.AddressViewPresenterImpl;
import com.cheersondemand.presenter.address.IAddressViewPresenter;
import com.cheersondemand.presenter.location.ILocationViewPresenter;
import com.cheersondemand.presenter.location.LocationViewPresenterImpl;
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
public class FragmentAddressList extends Fragment implements View.OnClickListener, ILocationViewPresenter.ILocationView,IAddressViewPresenter.IAddressView {


    @BindView(R.id.rvAddressList)
    RecyclerView rvAddressList;

    @BindView(R.id.rlView)
    RelativeLayout rlView;
    Unbinder unbinder;

    IAddressViewPresenter iAddressViewPresenter;
    ILocationViewPresenter iLocationViewPresenter;
    List<Address> addresses;
    Util util;
    AdapterAddress adapterAddress;
    LinearLayoutManager layoutManager;
    int position;
    @BindView(R.id.btnAddAnAddress)
    Button btnAddAnAddress;
    @BindView(R.id.llNoProductInCount)
    LinearLayout llNoProductInCount;
    int Address_id;
    boolean isLocationChanged=false;
    String addressName;
    boolean isFromLocationAndStoreScreen=false;
    public FragmentAddressList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iAddressViewPresenter = new AddressViewPresenterImpl(this, getActivity());
        iLocationViewPresenter=new LocationViewPresenterImpl(this,getActivity());
        if(getArguments()!=null) {
            Address_id = getArguments().getInt(C.ADDRESS_ID);
            addressName = getArguments().getString(C.ADDRESS_NAME);
            isLocationChanged=getArguments().getBoolean(C.IS_LOCATION_CHANGED);
            isFromLocationAndStoreScreen=getArguments().getBoolean(C.IS_FROM_LOCATION_AND_STORE_SCREEN);
        }
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
        btnAddAnAddress.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ActivityContainer)getActivity()).setTitle(getString(R.string.saved_addresses));

        getAddressList();

    }

    public void saveLocation(RecentLocation selectedLocation) {
        if(isLocationChanged) {
            SharedPreference.getInstance(getActivity()).setString(C.LOCATION_SELECTED, selectedLocation.getAddress());
            SharedPreference.getInstance(getActivity()).setLocation(C.SELECTED_LOCATION, selectedLocation);

            SaveLocation saveLocation = new SaveLocation();
            saveLocation.setLatitude("" + selectedLocation.getLatitude());
            saveLocation.setLongitude("" + selectedLocation.getLongitude());
            saveLocation.setUuid(Util.id(getActivity()));
            if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
                iLocationViewPresenter.saveLocation(saveLocation, "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId());
            } else {
                String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

                iLocationViewPresenter.saveLocation(token, saveLocation, id);

            }
        }
    }
    public void removeAddress(Address address, int pos) {
        position = pos;
        RemoveAddressRequest removeAddressRequest=new RemoveAddressRequest();
        removeAddressRequest.setUuid(Util.id(getActivity()));
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            int id=SharedPreference.getInstance(getActivity()).
                    geGuestUser(C.GUEST_USER).getId();
            iAddressViewPresenter.RemoveAddAddress(false,"", ""+id, "" + address.getId(),removeAddressRequest);

        }
        else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iAddressViewPresenter.RemoveAddAddress(true,token, id, "" + address.getId(),null);
        }
    }

    void getAddressList() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iAddressViewPresenter.getAddresses(false, "",id,Util.id(getActivity()));

        }else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iAddressViewPresenter.getAddresses(true, token, id, Util.id(getActivity()));
        }
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
                    SharedPreference.getInstance(getActivity()).setBoolean(C.IS_ANY_ADDRESS_ADDED,false);
                    llNoProductInCount.setVisibility(View.VISIBLE);
                    rvAddressList.setVisibility(View.GONE);
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

                    addresses = Response.getData();
                    adapterAddress = new AdapterAddress(addresses, getActivity(),addressName);
                    rvAddressList.setAdapter(adapterAddress);
                } else {
                    llNoProductInCount.setVisibility(View.VISIBLE);
                    rvAddressList.setVisibility(View.GONE);
                    gotoAddAddress();
                }
            } else {
                util.setSnackbarMessage(getActivity(), Response.getMessage(), rlView, true);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void gotoAddAddress(){
        getActivity().finish();
        Intent intent=new Intent(getActivity(), ActivityContainer.class);
        Bundle bundle=new Bundle();
        bundle.putBoolean(C.IS_EDIT,false);
        bundle.putBoolean(C.IS_FROM_CHECKOUT, false);
        bundle.putBoolean(C.IS_RETRY_PAYEMNT, false);
        bundle.putBoolean(C.IS_LOCATION_CHANGED, isLocationChanged);

        bundle.putSerializable(C.ADDRESS,adapterAddress.getSelectedAddress());

        intent.putExtra(C.BUNDLE,bundle);
        intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_ADD_ADDRESS);
        startActivity(intent);

    }
    @Override
    public void onAddDeliveryAddressSuccess(AddressAddResponse Response) {

    }

    @Override
    public void getSaveLocationSuccess(SaveLocationResponse response) {
        if (response.getSuccess()) {
            RecentLocation recentLocation=new RecentLocation();
            recentLocation.setId(adapterAddress.getSelectedAddress().getId());
            recentLocation.setAddress(adapterAddress.getSelectedAddress().getAddress());
            SharedPreference.getInstance(getActivity()).setString(C.LOCATION_SELECTED,adapterAddress.getSelectedAddress().getAddress());
            SharedPreference.getInstance(getActivity()).setLocation(C.SELECTED_LOCATION,recentLocation);
        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlView, true);

    }

    @Override
    public void onRecentLocationSuccess(RecentLocationResponse response) {

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
               gotoAddAddress();

                break;
        }
    }


}
