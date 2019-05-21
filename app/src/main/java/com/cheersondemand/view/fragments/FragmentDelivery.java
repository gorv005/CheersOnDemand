package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.location.RecentLocation;
import com.cheersondemand.model.location.RecentLocationResponse;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;
import com.cheersondemand.presenter.AuthenicationPresenterImpl;
import com.cheersondemand.presenter.IAutheniticationPresenter;
import com.cheersondemand.presenter.location.ILocationViewPresenter;
import com.cheersondemand.presenter.location.LocationViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.location.PlaceArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDelivery extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, ILocationViewPresenter.ILocationView, IAutheniticationPresenter.IAuthenticationView {
    Unbinder unbinder;
    @BindView(R.id.etAddLine)
    AutoCompleteTextView etAddLine;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    @BindView(R.id.ivCross)
    ImageView ivCross;
    @BindView(R.id.btnContinue)
    Button btnContinue;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final String LOG_TAG = "ADDRESS";
    private GoogleApiClient mGoogleApiClient;
    private int GOOGLE_API_CLIENT_ID = 0;
    RecentLocation selectedLocation;
    ILocationViewPresenter iLocationViewPresenter;
    Util util;
    IAutheniticationPresenter iAutheniticationPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedLocation = new RecentLocation();
        iLocationViewPresenter = new LocationViewPresenterImpl(this, getActivity());
        iAutheniticationPresenter = new AuthenicationPresenterImpl(this, getActivity());

        util = new Util();
    }

    public FragmentDelivery() {
        // Required empty public constructor
    }

    public FragmentDelivery newInstance(int client_id) {
        Bundle bundle = new Bundle();
        bundle.putInt(C.GOOGLE_API_CLIENT_ID, client_id);
        FragmentDelivery fragmentFirst = new FragmentDelivery();
        fragmentFirst.setArguments(bundle);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GOOGLE_API_CLIENT_ID = getArguments().getInt(C.GOOGLE_API_CLIENT_ID);
        etAddLine.setThreshold(1);
        etAddLine.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        etAddLine.setAdapter(mPlaceArrayAdapter);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        init();
    }


    void init() {
        btnContinue.setEnabled(false);
        etAddLine.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                if (etAddLine.getText().length() > 0) {


                    btnContinue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                    btnContinue.setEnabled(true);

                } else {
                    btnContinue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                    btnContinue.setEnabled(false);

                }

            }
        });
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            etAddLine.setText(item.description + "");

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(PlaceBuffer places) {
                    if (places.getCount() == 1) {
                        //Do the things here on Click.....
                        //      Toast.makeText(getApplicationContext(), String.valueOf(places.get(0).getLatLng()), Toast.LENGTH_SHORT).show();
                        selectedLocation.setLatitude(String.valueOf(places.get(0).getLatLng().latitude));
                        selectedLocation.setLongitude(String.valueOf(places.get(0).getLatLng().longitude));
                        selectedLocation.setAddress(String.valueOf(places.get(0).getAddress()));
                        //addAddress(selectedLocation);
                    } else {
                        //    Toast.makeText(getApplicationContext(), "SOMETHING_WENT_WRONG", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    public void saveLocation(RecentLocation selectedLocation) {
        SharedPreference.getInstance(getActivity()).setString(C.LOCATION_SELECTED, selectedLocation.getAddress());

        SaveLocation saveLocation = new SaveLocation();
        saveLocation.setLatitude("" + selectedLocation.getLatitude());
        saveLocation.setLongitude("" + selectedLocation.getLongitude());
        saveLocation.setAddress("" + selectedLocation.getAddress());

        saveLocation.setUuid(Util.id(getActivity()));
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iLocationViewPresenter.saveLocation(saveLocation, "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId());
        } else {
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            iLocationViewPresenter.saveLocation(token, saveLocation, id);

        }
    }

    void createGuestUser(){
        GenRequest categoryRequest = new GenRequest();
        categoryRequest.setUuid(Util.id(getActivity()));
        iAutheniticationPresenter.createGuestUser(categoryRequest);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());
    }

    @OnClick({R.id.ivCross, R.id.btnContinue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivCross:
                if (etAddLine.getText().toString().length() > 0) {
                    etAddLine.setText("");
                }
                break;
            case R.id.btnContinue:
                if (etAddLine.getText().toString().length() > 0) {
                    createGuestUser();
                }

                break;
        }
    }

    @Override
    public void getSaveLocationSuccess(SaveLocationResponse response) {
        if (response.getSuccess()) {

           /* if (from == C.SEARCH) {
                gotoStoreList();
            } else if (from == C.HOME) {
                getStoreList();
            }*/
            gotoLocationAndStoreList();
            getActivity().finish();

        } else {
            if (response.getErrors() != null) {
                util.setSnackbarMessage(getActivity(), response.getErrors().get(0).getField(), rlView, true);

            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
            }
        }
    }

    @Override
    public void getResponseSuccess(AuthenticationResponse response) {

    }

    @Override
    public void getResponseSuccessOfCreateGuestUser(GuestUserCreateResponse response) {
        try {
        if (response.getSuccess()) {
            if (Util.isNetworkConnectivity(getActivity())) {
                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_LOGIN_GUEST, true);

                SharedPreference.getInstance(getActivity()).seGuestUser(C.GUEST_USER, response.getData());
                saveLocation(selectedLocation);
            }
        } else {
            if (response.getMessage().trim().equalsIgnoreCase(C.GUEST_USER_ALLREADY_CREATED)) {
                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_LOGIN_GUEST, true);
                SharedPreference.getInstance(getActivity()).seGuestUser(C.GUEST_USER, response.getData());
                saveLocation(selectedLocation);
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void onRecentLocationSuccess(RecentLocationResponse response) {

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }

    void gotoLocationAndStoreList() {
        Intent intent = new Intent(getActivity(), ActivityContainer.class);
        Bundle bundle = new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_STORE_LOCATION_LIST);
        bundle.putInt(C.FROM, C.HOME);
        bundle.putBoolean(C.IS_CROSS_SHOW, false);

        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);

    }
}
