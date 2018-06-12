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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.view.ActivityContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangePassword extends Fragment implements View.OnClickListener {


    @BindView(R.id.tvPasswordError)
    TextView tvPasswordError;
    @BindView(R.id.etCurrentPassword)
    EditText etCurrentPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etReenterPassword)
    EditText etReenterPassword;
    @BindView(R.id.btnChangePassword)
    Button btnChangePassword;
    Unbinder unbinder;
    @BindView(R.id.btnGotoProfile)
    Button btnGotoProfile;
    @BindView(R.id.llpasswordChangeSuccess)
    LinearLayout llpasswordChangeSuccess;
    @BindView(R.id.llPasswordChangelayout)
    LinearLayout llPasswordChangelayout;

    public FragmentChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(R.string.change_password);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnChangePassword.setOnClickListener(this);
        btnGotoProfile.setOnClickListener(this);
    }

    void initFields() {
        btnChangePassword.setEnabled(false);
        etCurrentPassword.addTextChangedListener(new TextWatcher() {

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
        etNewPassword.addTextChangedListener(new TextWatcher() {

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
        etReenterPassword.addTextChangedListener(new TextWatcher() {

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
        if (etCurrentPassword.length() > 4 && etCurrentPassword.length() < 31) {

            if (etNewPassword.length() > 4 && etNewPassword.length() < 31) {

                if (etReenterPassword.length() > 4 && etReenterPassword.length() < 31) {

                    btnChangePassword.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                    btnChangePassword.setEnabled(true);
                } else {

                    btnChangePassword.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                    btnChangePassword.setEnabled(false);

                }

            } else {

                btnChangePassword.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                btnChangePassword.setEnabled(false);

            }
        } else {

            btnChangePassword.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
            btnChangePassword.setEnabled(false);

        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnChangePassword:
                break;
            case R.id.btnGotoProfile:
                getActivity().finish();
                break;
        }
    }
}
