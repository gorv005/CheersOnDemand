package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.address.Address;
import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressRequest;
import com.cheersondemand.model.address.AddressResponse;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.model.location.RecentLocation;
import com.cheersondemand.presenter.address.AddressViewPresenterImpl;
import com.cheersondemand.presenter.address.IAddressViewPresenter;
import com.cheersondemand.presenter.card.CardViewPresenterImpl;
import com.cheersondemand.presenter.card.ICardViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.location.PlaceArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddAddress extends Fragment implements View.OnClickListener, IAddressViewPresenter.IAddressView, ICardViewPresenter.ICardView,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {


    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etFlat)
    EditText etFlat;
    @BindView(R.id.etAddLine1)
    EditText etAddLine1;
    @BindView(R.id.etAddLine2)
    EditText etAddLine2;
    @BindView(R.id.etPincode)
    EditText etPincode;
    @BindView(R.id.etPhoneNo)
    EditText etPhoneNo;
    @BindView(R.id.btnSaveAdd)
    Button btnSaveAdd;
    Unbinder unbinder;
    boolean isEdit, isFromCheckOut, isRetryPayment = false,isAddedFirstTime;
    Address address1;
    IAddressViewPresenter iAddressViewPresenter;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    Util util;
    ICardViewPresenter iCardViewPresenter;
    @BindView(R.id.etAddLine)
    AutoCompleteTextView etAddLine;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final String LOG_TAG = "ADDRESS";
    boolean isLocationChanged=false;
    public FragmentAddAddress() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iAddressViewPresenter = new AddressViewPresenterImpl(this, getActivity());
        iCardViewPresenter = new CardViewPresenterImpl(this, getActivity());

        util = new Util();
        isEdit = getArguments().getBoolean(C.IS_EDIT);
        isRetryPayment = getArguments().getBoolean(C.IS_RETRY_PAYEMNT);

        isFromCheckOut = getArguments().getBoolean(C.IS_FROM_CHECKOUT);

            try {
                address1 = (Address) getArguments().getSerializable(C.ADDRESS);
                isLocationChanged=getArguments().getBoolean(C.IS_LOCATION_CHANGED);
                isAddedFirstTime =getArguments().getBoolean(C.IS_ADDED_FIRST_TIME);

            }
            catch (Exception e){
                e.printStackTrace();
            }

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isEdit) {
            ((ActivityContainer) getActivity()).setTitle(getString(R.string.edit_address));

        } else {
            ((ActivityContainer) getActivity()).setTitle(getString(R.string.add_address));

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSaveAdd.setOnClickListener(this);
        etAddLine.setThreshold(1);
        etAddLine.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        etAddLine.setAdapter(mPlaceArrayAdapter);
        initFields();
        fillFields();
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            etAddLine.setText(item.description + "");

            /*PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);*/
        }
    };

    void fillFields() {
        if (isEdit) {
            etName.setText(address1.getName());
            etFlat.setText(address1.getFlatNo());
            etAddLine.setText(address1.getAddress());
           /* etAddLine1.setText(address1.getAddressFirst());
            etAddLine2.setText(address1.getAddressSecond());*/
            etPincode.setText(address1.getZipCode());
            etPhoneNo.setText(address1.getPhoneNumber());
            btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
            btnSaveAdd.setEnabled(true);
            btnSaveAdd.setText(getString(R.string.update_address));
        }
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            etAddLine.setText(place.getAddress() + "");

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    void initFields() {
        btnSaveAdd.setEnabled(false);
        etName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                validationFields();


            }
        });
        etFlat.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                validationFields();


            }
        });
        etAddLine1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                validationFields();


            }
        });
        etAddLine2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                validationFields();


            }
        });
        etPincode.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                validationFields();


            }
        });
        etPhoneNo.addTextChangedListener(new AutoAddTextWatcher(etPhoneNo,
                "-",
                3, 6));
/*
        etPhoneNo.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

               */
/* if (etPhoneNo.getText().toString().length() ==3) {
                    etPhoneNo.setText(Util.handlePhoneNumber(etPhoneNo.getText().toString(), "-"));
                    etPhoneNo.setSelection(etPhoneNo.getText().toString().length());

                }
                else if (etPhoneNo.getText().toString().length() ==7) {
                    etPhoneNo.setText(Util.handlePhoneNumber(etPhoneNo.getText().toString(), "-"));
                    etPhoneNo.setSelection(etPhoneNo.getText().toString().length());

                }*//*

                validationFields();


            }
        });
*/
    }


    void validationFields() {
        if (etName.getText().length() > 0 && etName.length() < 31) {
            if (etFlat.getText().length() > 0) {

                if (etAddLine.getText().length() > 0) {

                    if (etPincode.getText().length() > 0) {

                        if (etPhoneNo.getText().length() > 0 && etPhoneNo.length() == 12) {


                            btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                            btnSaveAdd.setEnabled(true);

                        } else {

                            btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                            btnSaveAdd.setEnabled(false);

                        }
                    } else {
                        btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                        btnSaveAdd.setEnabled(false);
                    }


                } else {
                    btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                    btnSaveAdd.setEnabled(false);
                }
            } else {
                btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                btnSaveAdd.setEnabled(false);
            }

        } else {

            btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
            btnSaveAdd.setEnabled(false);

        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveAdd:
                if (isEdit) {
                    editAddress();
                } else if (isFromCheckOut) {
                    addDeliveryAddress();
                } else {
                    addAddress();
                }
                break;
        }
    }


    void editAddress() {

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setUuid(Util.id(getActivity()));
        Address address = new Address();
        address.setName(etName.getText().toString());
        address.setFlatNo(etFlat.getText().toString());
        address.setAddress(etAddLine.getText().toString());

      /*  address.setAddressFirst(etAddLine1.getText().toString());
        address.setAddressSecond(etAddLine2.getText().toString());*/
        address.setZipCode(etPincode.getText().toString());
        address.setPhoneNumber(etPhoneNo.getText().toString());
        addressRequest.setAddress(address);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            int id=SharedPreference.getInstance(getActivity()).
                    geGuestUser(C.GUEST_USER).getId();
            iAddressViewPresenter.EditAddAddress(false,"", ""+id, "" + address1.getId(), addressRequest);

        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iAddressViewPresenter.EditAddAddress(true,token, id, "" + address1.getId(), addressRequest);
        }
    }

 public    void addAddress() {

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setUuid(Util.id(getActivity()));
        Address address = new Address();
        address.setName(etName.getText().toString());
        address.setFlatNo(etFlat.getText().toString());
        address.setAddress(etAddLine.getText().toString());
       /* address.setAddressFirst(etAddLine1.getText().toString());
        address.setAddressSecond(etAddLine2.getText().toString());*/
        address.setZipCode(etPincode.getText().toString());
        address.setPhoneNumber(etPhoneNo.getText().toString());
        addressRequest.setAddress(address);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {

            int id=SharedPreference.getInstance(getActivity()).
                    geGuestUser(C.GUEST_USER).getId();
            iAddressViewPresenter.AddAddress(false,"",""+id, addressRequest);

        }
        else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iAddressViewPresenter.AddAddress(true,token, id, addressRequest);
        }
    }

    public void addDeliveryAddress() {
        AddressRequest addressRequest = new AddressRequest();
        Address address = new Address();
        address.setName(etName.getText().toString());
        address.setFlatNo(etFlat.getText().toString());
        address.setAddress(etAddLine.getText().toString());
       /* address.setAddressFirst(etAddLine1.getText().toString());
        address.setAddressSecond(etAddLine2.getText().toString());*/
        address.setZipCode(etPincode.getText().toString());
        address.setPhoneNumber(etPhoneNo.getText().toString());
        addressRequest.setAddress(address);
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
        String order_id = "" + SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iAddressViewPresenter.addDeliveryAddress(token, id, order_id, addressRequest);
    }

    void getCardList() {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iCardViewPresenter.getCardList(token, id);
    }

    @Override
    public void onRemoveAddressSuccess(AddressAddResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddAddressSuccess(AddressAddResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
                //getActivity().finish();
                gotoAddressList(response.getData());

            } else {
                if(response.getErrors()!=null && response.getErrors().size()>=0){
                    util.setSnackbarMessage(getActivity(), response.getErrors().get(0).getField(), rlView, true);
                }
                else {
                    util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void  gotoAddressList(Address address){

        if(isLocationChanged){
            if(address!=null && address.getAddress()!=null) {
                RecentLocation recentLocation = new RecentLocation();
                recentLocation.setId(address.getId());
                recentLocation.setAddress(address.getAddress());
                SharedPreference.getInstance(getActivity()).setString(C.LOCATION_SELECTED, address.getAddress());
                SharedPreference.getInstance(getActivity()).setLocation(C.SELECTED_LOCATION, recentLocation);
            }
        }
        getActivity().finish();

        Intent intent3 = new Intent(getActivity(), ActivityContainer.class);
        intent3.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_ADDRESS_LIST);
        Bundle bundle = new Bundle();
        bundle.putBoolean(C.IS_FROM_LOCATION_AND_STORE_SCREEN, true);
        if(address!=null && address.getAddress()!=null) {
            bundle.putInt(C.ADDRESS_ID, address.getId());
            bundle.putString(C.ADDRESS_NAME, address.getAddress());
        }
        bundle.putBoolean(C.IS_LOCATION_CHANGED, isLocationChanged);

        intent3.putExtra(C.BUNDLE, bundle);
        intent3.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_ADDRESS_LIST);
        startActivity(intent3);
    }
    @Override
    public void onEditAddressSuccess(AddressAddResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
               // getActivity().finish();
                if(isFromCheckOut){
                    getActivity().finish();
                }
                else {
                    gotoAddressList(response.getData());
                }
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public void gotoSelectedPage(){
        if(isFromCheckOut){
            getActivity().finish();
        }
        else {
            if(isAddedFirstTime){
                getActivity().finish();
            }
            else {
                gotoAddressList(address1);
            }
        }
    }
    @Override
    public void onAddressListSuccess(AddressResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddDeliveryAddressSuccess(AddressAddResponse Response) {
        try {

            if (Response.getSuccess()) {
                //  ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_PAYMENT_CONFIRMATION, null);
                getCardList();
            } else {
                if (Response.getErrors() != null) {
                    if(Response.getErrors().get(0).getField()!=null){
                        dialog(Response.getErrors().get(0).getField());
                    }
                    else if(Response.getErrors().get(0).getDetail()!=null){
                        dialog(Response.getErrors().get(0).getDetail());
                    }
                    else {
                        dialog(Response.getMessage());
                    }
               //     util.setSnackbarMessage(getActivity(), Response.getErrors().get(0).getField(), LLView, true);
                } else {
                    dialog(Response.getMessage());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessCardList(CardListResponse response) {
        if (response.getSuccess()) {
            if (response.getData() != null && response.getData().size() > 0) {
                if (isRetryPayment) {
                    getActivity().onBackPressed();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(C.IS_RETRY_PAYEMNT, false);

                    ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_PAYMENT_CONFIRMATION, bundle);

                }
            } else {
                // tvNoCardAvailable.setVisibility(View.VISIBLE);
                gotoCard();

            }
        } else {
            // tvNoCardAvailable.setVisibility(View.VISIBLE);

            gotoCard();

        }
    }

    @Override
    public void onSuccessAddCard(CardAddResponse response) {

    }

    void gotoCard() {
        Bundle bundle3 = new Bundle();

        bundle3.putBoolean(C.IS_FROM_CHECKOUT, true);
        bundle3.putBoolean(C.IS_RETRY_PAYEMNT, false);

        ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_ADD_CARD, bundle3);
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

    public class AutoAddTextWatcher implements TextWatcher {
        private CharSequence mBeforeTextChanged;
        private TextWatcher mTextWatcher;
        private int[] mArray_pos;
        private EditText mEditText;
        private String mAppentText;

        public AutoAddTextWatcher(EditText editText, String appendText, int... position) {
            this.mEditText = editText;
            this.mAppentText = appendText;
            this.mArray_pos = position.clone();
        }

        public AutoAddTextWatcher(EditText editText, String appendText, TextWatcher textWatcher, int... position) {
            this(editText, appendText, position);
            this.mTextWatcher = textWatcher;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mBeforeTextChanged = s.toString();

            if (mTextWatcher != null)
                mTextWatcher.beforeTextChanged(s, start, count, after);

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            for (int i = 0; i < mArray_pos.length; i++) {
                if (((mBeforeTextChanged.length() - mAppentText.length() * i) == (mArray_pos[i] - 1) &&
                        (s.length() - mAppentText.length() * i) == mArray_pos[i])) {
                    mEditText.append(mAppentText);

                    break;
                }

                if (((mBeforeTextChanged.length() - mAppentText.length() * i) == mArray_pos[i] &&
                        (s.length() - mAppentText.length() * i) == (mArray_pos[i] + 1))) {
                    int idx_start = mArray_pos[i] + mAppentText.length() * i;
                    int idx_end = Math.min(idx_start + mAppentText.length(), s.length());

                    String sub = mEditText.getText().toString().substring(idx_start, idx_end);

                    if (!sub.equals(mAppentText)) {
                        mEditText.getText().insert(s.length() - 1, mAppentText);
                    }

                    break;
                }

                if (mAppentText.length() > 1 &&
                        (mBeforeTextChanged.length() - mAppentText.length() * i) == (mArray_pos[i] + mAppentText.length()) &&
                        (s.length() - mAppentText.length() * i) == (mArray_pos[i] + mAppentText.length() - 1)) {
                    int idx_start = mArray_pos[i] + mAppentText.length() * i;
                    int idx_end = Math.min(idx_start + mAppentText.length(), s.length());

                    mEditText.getText().delete(idx_start, idx_end);

                    break;
                }

            }

            if (mTextWatcher != null)
                mTextWatcher.onTextChanged(s, start, before, count);

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mTextWatcher != null) {
                mTextWatcher.afterTextChanged(s);
            }
            validationFields();


        }

    }
}
