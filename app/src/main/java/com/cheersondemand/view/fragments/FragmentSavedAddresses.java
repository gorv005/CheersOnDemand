package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.location.RecentLocation;
import com.cheersondemand.model.location.RecentLocationResponse;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;
import com.cheersondemand.presenter.address.AddressViewPresenterImpl;
import com.cheersondemand.presenter.location.ILocationViewPresenter;
import com.cheersondemand.presenter.location.LocationViewPresenterImpl;
import com.cheersondemand.presenter.store.StoreViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.NonScrollListView;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.MainActivity;
import com.cheersondemand.view.adapter.location.AdapterSavedLocations;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSavedAddresses extends Fragment  implements ILocationViewPresenter.ILocationView{


    @BindView(R.id.lvAddressList)
    NonScrollListView lvAddressList;
    @BindView(R.id.rlRecentSearch)
    LinearLayout rlRecentSearch;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    Unbinder unbinder;
    Util util;
    ILocationViewPresenter iLocationViewPresenter;
    String recentLocation;
    RecentLocation mRecentLocations;
    AdapterSavedLocations adapterSavedLocations;
    int source;
    public FragmentSavedAddresses() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            iLocationViewPresenter = new LocationViewPresenterImpl(this, getActivity());

            util = new Util();
        }



    public FragmentSavedAddresses newInstance(int pos,int source) {
        Bundle bundle = new Bundle();
        bundle.putInt(C.POS, pos);
        bundle.putInt(C.FROM, source);
        FragmentSavedAddresses fragmentFirst = new FragmentSavedAddresses();
        fragmentFirst.setArguments(bundle);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_addresses, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        source = getArguments().getInt(C.FROM);
    }

    @Override
    public void onResume() {
        super.onResume();
        getRecentSearches();
    }

    public void saveLocation(RecentLocation selectedLocation) {
        SharedPreference.getInstance(getActivity()).setString(C.LOCATION_SELECTED, selectedLocation.getAddress());
        SharedPreference.getInstance(getActivity()).setLocation(C.SELECTED_LOCATION, selectedLocation);

        SaveLocation saveLocation = new SaveLocation();
        saveLocation.setLatitude("" + selectedLocation.getLatitude());
        saveLocation.setLongitude("" + selectedLocation.getLongitude());
        saveLocation.setAddress(selectedLocation.getAddress());

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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getSaveLocationSuccess(SaveLocationResponse response) {
        if (response.getSuccess()) {
            RecentLocation recentLocation = new RecentLocation();
            recentLocation.setId(adapterSavedLocations.getSelectedItem().getId());
            recentLocation.setAddress(adapterSavedLocations.getSelectedItem().getAddress());
            SharedPreference.getInstance(getActivity()).setLocation(C.SELECTED_LOCATION, recentLocation);
            SharedPreference.getInstance(getActivity()).setString(C.LOCATION_SELECTED, adapterSavedLocations.getSelectedItem().getAddress());

            ((ActivityContainer)getActivity()).getStoresList();
        } else {
            if (response.getErrors() != null) {
                util.setSnackbarMessage(getActivity(), response.getErrors().get(0).getField(), rlView, true);

            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
            }
        }
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void onRecentLocationSuccess(RecentLocationResponse response) {
        if (response.getSuccess()) {
            rlRecentSearch.setVisibility(View.VISIBLE);

            if (response.getData() != null && response.getData().size() > 0) {
                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_ANY_ADDRESS_ADDED, true);
                recentLocation = SharedPreference.getInstance(getActivity()).getString(C.LOCATION_SELECTED);
                mRecentLocations = SharedPreference.getInstance(getActivity()).getLocation(C.SELECTED_LOCATION);
                adapterSavedLocations = new AdapterSavedLocations(source, getActivity(), response.getData(), recentLocation, mRecentLocations);
                lvAddressList.setAdapter(adapterSavedLocations);
                if (recentLocation != null || mRecentLocations != null) {
               //     getStoreList();
                    ((ActivityContainer)getActivity()).getStoresList();
                }
            } /*else {
                gotoSearch();
            }*/
        }
    }

   public void gotoSearch() {

        Intent intent = new Intent(getActivity(), MainActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_ADDRESS_PICKUP_DELIVERY_SELECTION);
        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);

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
