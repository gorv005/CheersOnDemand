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

import com.cheersondemand.R;
import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUpdateProfile extends Fragment {


    @BindView(R.id.imgProfile)
    RoundedImageView imgProfile;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhoneNo)
    EditText etPhoneNo;
    @BindView(R.id.btnSaveProfile)
    Button btnSaveProfile;
    Unbinder unbinder;
    AuthenticationResponse authenticationResponse;
    public FragmentUpdateProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    authenticationResponse= SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER);
      initFields();

    etName.setText(authenticationResponse.getData().getUser().getName());
    etEmail.setText(authenticationResponse.getData().getUser().getEmail());
    etPhoneNo.setText(authenticationResponse.getData().getUser().getPhoneNumber());

    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(R.string.edit_profile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    void initFields(){
        btnSaveProfile.setEnabled(false);
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
    void  validationFields(){
        if(etName.getText().length()>2 && etName.length()<31){

            if(Util.isValidPhone(etPhoneNo.getText().toString())){


                btnSaveProfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                btnSaveProfile.setEnabled(true);

            }
            else {

                btnSaveProfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                btnSaveProfile.setEnabled(false);

            }
        }
        else {

            btnSaveProfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
            btnSaveProfile.setEnabled(false);

        }


    }


}
