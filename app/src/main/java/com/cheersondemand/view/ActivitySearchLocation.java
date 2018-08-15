package com.cheersondemand.view;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheersondemand.R;
import com.cheersondemand.model.location.RecentLocation;
import com.cheersondemand.model.location.RecentLocationResponse;
import com.cheersondemand.model.location.SaveLocation;
import com.cheersondemand.model.location.SaveLocationResponse;
import com.cheersondemand.model.store.StoreListResponse;
import com.cheersondemand.model.store.UpdateStoreResponse;
import com.cheersondemand.presenter.location.ILocationViewPresenter;
import com.cheersondemand.presenter.location.LocationViewPresenterImpl;
import com.cheersondemand.presenter.store.IStoreViewPresenter;
import com.cheersondemand.presenter.store.StoreViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.util.location.LocationHelper;
import com.cheersondemand.view.adapter.location.AdapterLocation;
import com.cheersondemand.view.adapter.location.AdapterRecentSearches;
import com.cheersondemand.view.adapter.location.RecyclerItemClickListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitySearchLocation extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, ILocationViewPresenter.ILocationView, View.OnClickListener, IStoreViewPresenter.IStoreView {
    private static final String LOG_TAG = "ActivitySearchLocation";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    @BindView(R.id.rvSearchResult)
    RecyclerView rvSearchResult;
    @BindView(R.id.rlLocationSearch)
    LinearLayout rlLocationSearch;
    @BindView(R.id.rvRecentSearches)
    RecyclerView rvRecentSearches;
    @BindView(R.id.rlRecentSearch)
    LinearLayout rlRecentSearch;
    @BindView(R.id.imgBack)
    RelativeLayout imgBack;
    @BindView(R.id.etLocation)
    EditText autoCompleteTextView;
    @BindView(R.id.LLView)
    LinearLayout LLView;
    ILocationViewPresenter iLocationViewPresenter;
    AdapterRecentSearches adapterRecentSearches;
    int from;
    Util util;
    LocationHelper locationHelper;
    @BindView(R.id.rlCurrentLocation)
    RelativeLayout rlCurrentLocation;
    @BindView(R.id.tvRecentSearch)
    TextView tvRecentSearch;
    // private EditText mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;
    private AdapterLocation adapterLocation;
    private LinearLayoutManager mLinearLayoutManager;
    private Location mLastLocation;
    IStoreViewPresenter iStoreViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_location);
        iStoreViewPresenter = new StoreViewPresenterImpl(this, this);
        from = getIntent().getIntExtra(C.FROM, 1);
        buildAPIClient();
        ButterKnife.bind(this);
        imgBack.setOnClickListener(this);
        rlCurrentLocation.setOnClickListener(this);
        util = new Util();
        iLocationViewPresenter = new LocationViewPresenterImpl(this, this);
        init();
        locationHelper = new LocationHelper(this);
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
        }
        getRecentSearches();
    }

    void getRecentSearches() {
        if (SharedPreference.getInstance(ActivitySearchLocation.this).getBoolean(C.IS_LOGIN_GUEST)) {
            iLocationViewPresenter.getRecentLocation(false, "", Util.id(this), "" + SharedPreference.getInstance(ActivitySearchLocation.this).geGuestUser(C.GUEST_USER).getId());
        } else {
            String token = C.bearer + SharedPreference.getInstance(ActivitySearchLocation.this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            String id = "" + SharedPreference.getInstance(ActivitySearchLocation.this).getUser(C.AUTH_USER).getData().getUser().getId();

            iLocationViewPresenter.getRecentLocation(true, token, Util.id(this), "" + id);

        }
    }
   /* void setRecentSearches() {
        rlRecentSearch.setVisibility(View.VISIBLE);
        rlLocationSearch.setVisibility(View.GONE);
        List<SelectedLocation> selectedLocations = SharedPreference.getInstance(ActivitySearchLocation.this).getRecentLocations(C.LOCATION_SELECTED);
        if (selectedLocations != null && selectedLocations.size() > 0) {

            adapterRecentSearches = new AdapterRecentSearches(selectedLocations, ActivitySearchLocation.this);
            mLinearLayoutManager = new LinearLayoutManager(this);
            rvRecentSearches.setLayoutManager(mLinearLayoutManager);
            rvRecentSearches.setAdapter(adapterRecentSearches);
        }
    }*/

    void init() {
        if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN_GUEST)) {
            imgBack.setVisibility(View.VISIBLE);
        } else {
            imgBack.setVisibility(View.GONE);
        }
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (autoCompleteTextView.getText().toString().length() == 0) {
                    rlRecentSearch.setVisibility(View.VISIBLE);
                    rlLocationSearch.setVisibility(View.GONE);
                } else {
                    rlRecentSearch.setVisibility(View.GONE);
                    rlLocationSearch.setVisibility(View.VISIBLE);
                }
                if (!s.toString().equals("") && mGoogleApiClient.isConnected()) {
                    adapterLocation.getFilter().filter(s.toString());
                } else if (!mGoogleApiClient.isConnected()) {
                    Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT).show();

                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });


        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                 //.setCountry("US")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        adapterLocation = new AdapterLocation(this, R.layout.item_address,
                mGoogleApiClient, null, autocompleteFilter);
//        mAutocompleteTextView.setAdapter(mAdapterAddress);

        mLinearLayoutManager = new LinearLayoutManager(this);
        rvSearchResult.setLayoutManager(mLinearLayoutManager);
        rvSearchResult.setAdapter(adapterLocation);


        rvSearchResult.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {

                            final RecentLocation selectedLocation = new RecentLocation();
                            final AdapterLocation.PlaceAutocomplete item = adapterLocation.getItem(position);
                            final String placeId = String.valueOf(item.placeId);
                            Log.d("TAG", "Autocomplete item selected: " + item.description);
                            selectedLocation.setAddress(item.description.toString());
                        /*
                             Issue a request to the Places Geo Data API to retrieve a Place object with additional details about the place.
                         */
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
                                        saveLocation(selectedLocation);

                                    } else {
                                        //    Toast.makeText(getApplicationContext(), "SOMETHING_WENT_WRONG", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            Log.d("TAG", "Clicked: " + item.description);
                            Log.d("TAG", "Called getPlaceById to get Place details for " + item.placeId);


                           /* Intent intent = new Intent();
                            intent.putExtra(addressFor, item.description);
                            setResult(C.RESULT_ADDRESS, intent);
                            finish();*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }


    public void saveLocation(RecentLocation selectedLocation) {
        SharedPreference.getInstance(ActivitySearchLocation.this).setString(C.LOCATION_SELECTED, selectedLocation.getAddress());

        SaveLocation saveLocation = new SaveLocation();
        saveLocation.setLatitude("" + selectedLocation.getLatitude());
        saveLocation.setLongitude("" + selectedLocation.getLongitude());
        saveLocation.setUuid(Util.id(ActivitySearchLocation.this));
        if (SharedPreference.getInstance(ActivitySearchLocation.this).getBoolean(C.IS_LOGIN_GUEST)) {
            iLocationViewPresenter.saveLocation(saveLocation, "" + SharedPreference.getInstance(ActivitySearchLocation.this).geGuestUser(C.GUEST_USER).getId());
        } else {
            String token = C.bearer + SharedPreference.getInstance(ActivitySearchLocation.this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            String id = "" + SharedPreference.getInstance(ActivitySearchLocation.this).getUser(C.AUTH_USER).getData().getUser().getId();

            iLocationViewPresenter.saveLocation(token, saveLocation, id);

        }
    }

    void buildAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(ActivitySearchLocation.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v("Google API Callback", "Connection Done");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("Google API Callback", "Connection Suspended");
        Log.v("Code", String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("Google API Callback", "Connection Failed");
        Log.v("Error Code", String.valueOf(connectionResult.getErrorCode()));
        Toast.makeText(this, "API_NOT_CONNECTED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();

        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
            Log.v("Google API", "Connecting");
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        /*if (mGoogleApiClient.isConnected()) {
            Log.v("Google API", "Dis-Connecting");
            mGoogleApiClient.disconnect();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //location();
    }

    void location() {
        locationHelper.checkpermission();

        mLastLocation = locationHelper.getLocation();
        if (mLastLocation != null) {

            final RecentLocation selectedLocation = new RecentLocation();
            Address address = locationHelper.getAddress(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            selectedLocation.setAddress(address.getLocality() + ", " + address.getAdminArea() + ", " + address.getCountryName());

            selectedLocation.setLatitude("" + mLastLocation.getLatitude());
            selectedLocation.setLongitude("" + mLastLocation.getLongitude());
            saveLocation(selectedLocation);
        } else {
            if(locationHelper.isPermissionGranted()) {
                util.setSnackbarMessage(this, "Couldn't get the location. Make sure location is enabled on the device", LLView, true);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void getSaveLocationSuccess(SaveLocationResponse response) {
        if (response.getSuccess()) {

            if (from == C.SEARCH) {
                gotoStoreList();
            } else if (from == C.HOME) {
                getStoreList();
            }
        } else {
            if(response.getErrors()!=null){
                util.setSnackbarMessage(this,response.getErrors().get(0).getField(), LLView, true);

            }
            else {
                util.setSnackbarMessage(this, response.getMessage(), LLView, true);
            }
        }

    }


    void gotoStoreList() {
        Intent intent = new Intent(this, ActivityContainer.class);
        Bundle bundle = new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_STORE_LIST);
        bundle.putInt(C.FROM, from);

        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);
    }

    void getStoreList() {
        if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN_GUEST)) {
            iStoreViewPresenter.getStoreList(Util.id(this));
        } else {

            String token = "bearer " + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();

            iStoreViewPresenter.getStoreList(token, Util.id(this));

        }
    }

    @Override
    public void getStoreListSuccess(StoreListResponse response) {
        if (!response.getSuccess()) {
            SharedPreference.getInstance(this).setStore(C.SELECTED_STORE, null);
            SharedPreference.getInstance(this).setString(C.STORE_MSG, response.getMessage());
            finish();
        } else {
            gotoStoreList();
        }
    }

    @Override
    public void updateStoreSuccess(UpdateStoreResponse response) {

    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(this, response, LLView, true);

    }

    @Override
    public void onRecentLocationSuccess(RecentLocationResponse response) {
        if (response.getSuccess()) {
            rlRecentSearch.setVisibility(View.VISIBLE);
            rlLocationSearch.setVisibility(View.GONE);

            if (response.getData() != null && response.getData().size() > 0) {
                tvRecentSearch.setVisibility(View.VISIBLE);

                adapterRecentSearches = new AdapterRecentSearches(response.getData(), ActivitySearchLocation.this);
                mLinearLayoutManager = new LinearLayoutManager(this);
                rvRecentSearches.setLayoutManager(mLinearLayoutManager);
                rvRecentSearches.setAdapter(adapterRecentSearches);
            }
            else {
                tvRecentSearch.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showProgress() {
        try {
            util.showDialog(C.MSG, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideProgress() {
        try {

            util.hideDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.rlCurrentLocation:
                location();
                break;
        }
    }
}
