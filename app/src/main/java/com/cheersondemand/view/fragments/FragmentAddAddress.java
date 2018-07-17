package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.address.Address;
import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressRequest;
import com.cheersondemand.model.address.AddressResponse;
import com.cheersondemand.presenter.address.AddressViewPresenterImpl;
import com.cheersondemand.presenter.address.IAddressViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddAddress extends Fragment implements View.OnClickListener, IAddressViewPresenter.IAddressView {


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
    boolean isEdit,isFromCheckOut;
    Address address1;
    IAddressViewPresenter iAddressViewPresenter;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    Util util;

    public FragmentAddAddress() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iAddressViewPresenter = new AddressViewPresenterImpl(this, getActivity());
        util=new Util();
        isEdit = getArguments().getBoolean(C.IS_EDIT);
        isFromCheckOut = getArguments().getBoolean(C.IS_FROM_CHECKOUT);

        if (isEdit) {
            address1 = (Address) getArguments().getSerializable(C.ADDRESS);
        }
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
        if(isEdit) {
            ActivityContainer.tvTitle.setText(getString(R.string.edit_address));
        }
        else {
            ActivityContainer.tvTitle.setText(getString(R.string.add_address));

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSaveAdd.setOnClickListener(this);
        initFields();
        fillFields();
    }


    void fillFields() {
        if (isEdit) {
            etName.setText(address1.getName());
            etFlat.setText(address1.getFlatNo());
            etAddLine1.setText(address1.getAddressFirst());
            etAddLine2.setText(address1.getAddressSecond());
            etPincode.setText(address1.getZipCode());
            etPhoneNo.setText(address1.getPhoneNumber());
            btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
            btnSaveAdd.setEnabled(true);
            btnSaveAdd.setText(getString(R.string.update_address));
        }
    }

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

        etPhoneNo.addTextChangedListener(new TextWatcher() {

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
    }


    void validationFields() {
        if (etName.getText().length() > 0 && etName.length() < 31) {
            if (etFlat.getText().length() > 0) {

                if (etAddLine1.getText().length() > 0) {
                    if (etAddLine2.getText().length() > 0) {

                        if (etPincode.getText().length() > 0) {

                            if (etPhoneNo.getText().length() > 0 && etPhoneNo.length() == 10) {


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
                }

               else if(isFromCheckOut){
                    addDeliveryAddress();
                }
                else {
                    addAddress();
                }
                break;
        }
    }


    void editAddress() {

        AddressRequest addressRequest = new AddressRequest();
        Address address = new Address();
        address.setName(etName.getText().toString());
        address.setFlatNo(etFlat.getText().toString());
        address.setAddressFirst(etAddLine1.getText().toString());
        address.setAddressSecond(etAddLine2.getText().toString());
        address.setZipCode(etPincode.getText().toString());
        address.setPhoneNumber(etPhoneNo.getText().toString());
        addressRequest.setAddress(address);

        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iAddressViewPresenter.EditAddAddress(token, id, "" + address1.getId(), addressRequest);
    }

    void addAddress() {

        AddressRequest addressRequest = new AddressRequest();
        Address address = new Address();
        address.setName(etName.getText().toString());
        address.setFlatNo(etFlat.getText().toString());
        address.setAddressFirst(etAddLine1.getText().toString());
        address.setAddressSecond(etAddLine2.getText().toString());
        address.setZipCode(etPincode.getText().toString());
        address.setPhoneNumber(etPhoneNo.getText().toString());
        addressRequest.setAddress(address);

        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iAddressViewPresenter.AddAddress(token, id, addressRequest);
    }
    public void addDeliveryAddress() {
        AddressRequest addressRequest = new AddressRequest();
        Address address = new Address();
        address.setName(etName.getText().toString());
        address.setFlatNo(etFlat.getText().toString());
        address.setAddressFirst(etAddLine1.getText().toString());
        address.setAddressSecond(etAddLine2.getText().toString());
        address.setZipCode(etPincode.getText().toString());
        address.setPhoneNumber(etPhoneNo.getText().toString());
        addressRequest.setAddress(address);
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
        String order_id = "" + SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iAddressViewPresenter.addDeliveryAddress(token, id, order_id,addressRequest);
    }
    @Override
    public void onRemoveAddressSuccess(AddressAddResponse response) {
        if(response.getSuccess()){
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void onAddAddressSuccess(AddressAddResponse response) {
        if(response.getSuccess()){
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
            getActivity().finish();

        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void onEditAddressSuccess(AddressAddResponse response) {
        if(response.getSuccess()){
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
            getActivity().finish();
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void onAddressListSuccess(AddressResponse response) {
        if(response.getSuccess()){
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void onAddDeliveryAddressSuccess(AddressAddResponse Response) {
        if(Response.getSuccess()){

        }
        else {
            dialog(Response.getMessage());
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
