package com.cheersondemand.view.fragments;


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

import com.cheersondemand.R;
import com.cheersondemand.model.store.AddStore;
import com.cheersondemand.model.store.UpdateStoreResponse;
import com.cheersondemand.model.store.Warehouse;
import com.cheersondemand.presenter.store.AddStoreViewPresenterImpl;
import com.cheersondemand.presenter.store.IAddStoreViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBecomePartner extends Fragment implements View.OnClickListener,IAddStoreViewPresenter.IAddStoreView{


    @BindView(R.id.etStoreName)
    EditText etStoreName;
    @BindView(R.id.etContactNo)
    EditText etContactNo;
    @BindView(R.id.etAddLine1)
    EditText etAddLine1;
    @BindView(R.id.etAddLine2)
    EditText etAddLine2;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.btnSubmitRequest)
    Button btnSubmitRequest;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    Unbinder unbinder;
    Util util;
    IAddStoreViewPresenter iAddStoreViewPresenter;
    public FragmentBecomePartner() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(getString(R.string.become_partner));
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util=new Util();
        iAddStoreViewPresenter=new AddStoreViewPresenterImpl(this,getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_become_patner, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSubmitRequest.setOnClickListener(this);
        initFields();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    void initFields() {
        btnSubmitRequest.setEnabled(false);
        etStoreName.addTextChangedListener(new TextWatcher() {

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
        etContactNo.addTextChangedListener(new TextWatcher() {

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
        etEmail.addTextChangedListener(new TextWatcher() {

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
        if (etStoreName.getText().length() > 0 && etStoreName.length() < 31) {
            if (etContactNo.getText().length() > 0 && etContactNo.length() == 10) {

                if (etAddLine1.getText().length() > 0) {
                    if (etAddLine2.getText().length() > 0) {

                        if (etEmail.getText().length() > 0) {

                            btnSubmitRequest.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                            btnSubmitRequest.setEnabled(true);
                        } else {
                            btnSubmitRequest.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                            btnSubmitRequest.setEnabled(false);
                        }
                    } else {
                        btnSubmitRequest.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                        btnSubmitRequest.setEnabled(false);
                    }

                } else {
                    btnSubmitRequest.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                    btnSubmitRequest.setEnabled(false);
                }
            } else {
                btnSubmitRequest.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                btnSubmitRequest.setEnabled(false);
            }

        } else {

            btnSubmitRequest.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
            btnSubmitRequest.setEnabled(false);

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmitRequest:
                addStore();
                break;
        }
    }


    void addStore(){
        AddStore addStore=new AddStore();
        Warehouse warehouse=new Warehouse();
        warehouse.setName(etStoreName.getText().toString());
        warehouse.setAddress(etAddLine1.getText().toString() +" "+etAddLine2.getText().toString());
        warehouse.setContactNumber(etContactNo.getText().toString());
        warehouse.setEmail(etEmail.getText().toString());
        iAddStoreViewPresenter.addStore(addStore);

    }
    @Override
    public void onAddStoreSuccess(UpdateStoreResponse Response) {
        if(Response.getSuccess()){
            etStoreName.setText("");
            etAddLine1.setText("");
            etAddLine2.setText("");
            etEmail.setText("");
            etContactNo.setText("");
            util.setSnackbarMessage(getActivity(), Response.getMessage(), rlView, true);

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
}
