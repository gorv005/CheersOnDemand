package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.location.RecentLocation;
import com.cheersondemand.model.location.RecentLocationResponse;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;
import com.cheersondemand.model.store.StoreList;
import com.cheersondemand.model.store.StoreListResponse;
import com.cheersondemand.model.store.UpdateStore;
import com.cheersondemand.model.store.UpdateStoreResponse;
import com.cheersondemand.presenter.location.ILocationViewPresenter;
import com.cheersondemand.presenter.location.LocationViewPresenterImpl;
import com.cheersondemand.presenter.store.IStoreViewPresenter;
import com.cheersondemand.presenter.store.StoreViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.NonScrollListView;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.StoreProducts;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityHome;
import com.cheersondemand.view.adapter.location.AdapterSavedLocations;
import com.cheersondemand.view.adapter.store.AdapterStoresListing;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSelectAddressAndStore extends Fragment implements ILocationViewPresenter.ILocationView, View.OnClickListener, IStoreViewPresenter.IStoreView {


    @BindView(R.id.imgBack)
    RelativeLayout imgBack;
    @BindView(R.id.lvAddressList)
    NonScrollListView lvAddressList;
    @BindView(R.id.rlRecentSearch)
    LinearLayout rlRecentSearch;
    @BindView(R.id.lvStoreList)
    NonScrollListView lvStoreList;
    @BindView(R.id.rlStoreList)
    LinearLayout rlStoreList;
    @BindView(R.id.rlStoreView)
    RelativeLayout rlStoreView;
    @BindView(R.id.LLView)
    RelativeLayout LLView;
    Unbinder unbinder;
    AdapterSavedLocations adapterSavedLocations;
    ILocationViewPresenter iLocationViewPresenter;
    IStoreViewPresenter iStoreViewPresenter;
    Util util;
    List<StoreList> storeList;
    int source;
    String recentLocation;
    RecentLocation mRecentLocations;
    StoreList store;
    AdapterStoresListing adapterStore;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.llStoreList)
    LinearLayout llStoreList;
    @BindView(R.id.rlAddNewAddress)
    RelativeLayout rlAddNewAddress;

    public FragmentSelectAddressAndStore() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iLocationViewPresenter = new LocationViewPresenterImpl(this, getActivity());
        iStoreViewPresenter = new StoreViewPresenterImpl(this, getActivity());
        source = getArguments().getInt(C.FROM);

        util = new Util();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_select_address_and_store, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSubmit.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        rlAddNewAddress.setOnClickListener(this);
        btnSubmit.setEnabled(false);
        llStoreList.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((ActivityContainer) getActivity()).hideToolBar();
        getRecentSearches();
    }

    void getStoreList() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iStoreViewPresenter.getStoreList(Util.id(getActivity()));
        } else {

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();

            iStoreViewPresenter.getStoreList(token, Util.id(getActivity()));

        }
    }

    public void saveLocation(RecentLocation selectedLocation) {
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

    void getRecentSearches() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iLocationViewPresenter.getRecentLocation(false, "", Util.id(getActivity()), "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId());
        } else {
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            iLocationViewPresenter.getRecentLocation(true, token, Util.id(getActivity()), "" + id);

        }
    }


    public void enableButton() {
        btnSubmit.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
        btnSubmit.setEnabled(true);
    }

    public void disableButton() {
        btnSubmit.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
        btnSubmit.setEnabled(false);
    }

    void updateStore() {
        store = adapterStore.getSelectedItem();
        if (store != null && storeList != null) {

            UpdateStore updateStore = new UpdateStore();
            updateStore.setUuid(Util.id(getActivity()));
            updateStore.setWarehouseId(store.getId());
            if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {

                iStoreViewPresenter.updateStore("" + SharedPreference.getInstance(getActivity()).
                        geGuestUser(C.GUEST_USER).getId(), updateStore);
            } else {
                String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

                iStoreViewPresenter.updateStore(token, id, updateStore);
            }
        } else {
            util.setSnackbarMessage(getActivity(), getString(R.string.plz_select_store), LLView, true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                updateStore();
                break;
            case R.id.imgBack:
                getActivity().finish();
                break;
            case R.id.rlAddNewAddress:
                Intent intent=new Intent(getActivity(), ActivityContainer.class);
                Bundle bundle=new Bundle();
                bundle.putBoolean(C.IS_EDIT,false);
                bundle.putBoolean(C.IS_FROM_CHECKOUT, false);
                bundle.putBoolean(C.IS_RETRY_PAYEMNT, false);

                intent.putExtra(C.BUNDLE,bundle);
                intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_ADD_ADDRESS);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getSaveLocationSuccess(SaveLocationResponse response) {
        if (response.getSuccess()) {
            getStoreList();
        } else {
            if (response.getErrors() != null) {
                util.setSnackbarMessage(getActivity(), response.getErrors().get(0).getField(), LLView, true);

            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);
            }
        }

    }

    @Override
    public void getStoreListSuccess(StoreListResponse response) {
        try {
            if (response.getSuccess()) {
                storeList = response.getData();
                if (storeList != null && storeList.size() > 0) {
                    llStoreList.setVisibility(View.VISIBLE);
                    ((ActivityContainer) getActivity()).hideToolBar();
                    StoreList storeList1 = SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE);
                    adapterStore = new AdapterStoresListing(source, getActivity(), storeList, storeList1);
                    lvStoreList.setAdapter(adapterStore);
                }
            } else {
                llStoreList.setVisibility(View.GONE);
                util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateStoreSuccess(UpdateStoreResponse response) {
        try {
            if (response.getSuccess()) {
                StoreProducts.getInstance().clear();
                if (response.getData() != null && response.getData().getIsQuantityUpdated()) {
                    SharedPreference.getInstance(getActivity()).setBoolean(C.IS_QUANTITY_UPDATED, response.getData().getIsQuantityUpdated());
                } else {
                    SharedPreference.getInstance(getActivity()).setBoolean(C.IS_QUANTITY_UPDATED, false);

                }
                SharedPreference.getInstance(getActivity()).setStore(C.SELECTED_STORE, store);
                if (source == C.SEARCH) {
                    showHome();
                } else if (source == C.HOME) {
                    showHome();
                } else if (source == C.FRAGMENT_PRODUCT_LISTING) {
                    SharedPreference.getInstance(getActivity()).setBoolean(C.IS_STORE_UPDATED, true);
                    getActivity().onBackPressed();
                }
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoHome() {
        try {
            Intent intent = new Intent(getActivity(), ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getActivity().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showHome() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_SHOW_FROM_MAIL)) {
            String s = SharedPreference.getInstance(getActivity()).getString(C.SOURCE);
            if (s != null) {
                gotoSource(Integer.parseInt(s));
            } else {
                gotoHome();
            }
        } else {
            gotoHome();
        }
    }

    public void gotoSource(int source) {
        SharedPreference.getInstance(getActivity()).setBoolean(C.IS_SHOW_FROM_MAIL, false);
        Intent intent = new Intent(getActivity(), ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putInt(C.FRAGMENT_ACTION, source);
        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, LLView, true);

    }


    @Override
    public void onRecentLocationSuccess(RecentLocationResponse response) {
        if (response.getSuccess()) {
            rlRecentSearch.setVisibility(View.VISIBLE);

            if (response.getData() != null && response.getData().size() > 0) {
                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_ANY_ADDRESS_ADDED,true);
                recentLocation = SharedPreference.getInstance(getActivity()).getString(C.LOCATION_SELECTED);
                mRecentLocations = SharedPreference.getInstance(getActivity()).getLocation(C.SELECTED_LOCATION);
                adapterSavedLocations = new AdapterSavedLocations(source, getActivity(), response.getData(), recentLocation, mRecentLocations);
                lvAddressList.setAdapter(adapterSavedLocations);
                if (recentLocation != null || mRecentLocations != null) {
                    getStoreList();
                }
            }
        }
    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }
}
