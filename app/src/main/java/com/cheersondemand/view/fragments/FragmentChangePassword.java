package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.changepassword.Password;
import com.cheersondemand.model.changepassword.PasswordRequest;
import com.cheersondemand.model.changepassword.PasswordResponse;
import com.cheersondemand.presenter.password.IPasswordViewPresenter;
import com.cheersondemand.presenter.password.PasswordViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.CustomEditText;
import com.cheersondemand.util.DrawableClickListener;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangePassword extends Fragment implements View.OnClickListener, IPasswordViewPresenter.IPasswordView {


    @BindView(R.id.tvPasswordError)
    TextView tvPasswordError;
    @BindView(R.id.etCurrentPassword)
    CustomEditText etCurrentPassword;
    @BindView(R.id.etNewPassword)
    CustomEditText etNewPassword;
    @BindView(R.id.etReenterPassword)
    CustomEditText etReenterPassword;
    @BindView(R.id.btnChangePassword)
    Button btnChangePassword;
    Unbinder unbinder;
    @BindView(R.id.btnGotoProfile)
    Button btnGotoProfile;
    @BindView(R.id.llpasswordChangeSuccess)
    LinearLayout llpasswordChangeSuccess;
    @BindView(R.id.llPasswordChangelayout)
    LinearLayout llPasswordChangelayout;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    Util util;
    boolean isPasswordVisibleCurrent=false,isPasswordVisibleNew=false,isPasswordVisibleConfirmNew=false;
    IPasswordViewPresenter iPasswordViewPresenter;
    public FragmentChangePassword() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util=new Util();
        iPasswordViewPresenter=new PasswordViewPresenterImpl(this,getActivity());
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
        initFields();
    }

    void initFields() {


        btnChangePassword.setEnabled(false);

        etCurrentPassword.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        if(!isPasswordVisibleCurrent) {
                            isPasswordVisibleCurrent=true;
                            etCurrentPassword.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etCurrentPassword.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_eye_visible, 0);

                        }
                        else {
                            isPasswordVisibleCurrent=false;
                            etCurrentPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etCurrentPassword.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_eye, 0);

                        }
                        break;

                    default:
                        break;
                }
            }

        });
        etNewPassword.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        if(!isPasswordVisibleNew) {
                            isPasswordVisibleNew=true;
                            etCurrentPassword.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etCurrentPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_visible, 0);

                        }
                        else {
                            isPasswordVisibleNew=false;
                            etCurrentPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etCurrentPassword.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_eye, 0);

                        }
                        break;

                    default:
                        break;
                }
            }

        });
        etReenterPassword.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        if(!isPasswordVisibleConfirmNew) {
                            isPasswordVisibleConfirmNew=true;
                            etCurrentPassword.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etCurrentPassword.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_eye_visible, 0);

                        }
                        else {
                            isPasswordVisibleConfirmNew=false;
                            etCurrentPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etCurrentPassword.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_eye, 0);

                        }
                        break;

                    default:
                        break;
                }
            }

        });
        etCurrentPassword.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                    if(etCurrentPassword.getText().toString().length()>0){
                        etCurrentPassword.setCompoundDrawablesWithIntrinsicBounds( R.drawable.password_key, 0, R.drawable.ic_eye, 0);

                    }

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

                if(etNewPassword.getText().toString().length()>0){
                    etNewPassword.setCompoundDrawablesWithIntrinsicBounds( R.drawable.password_key, 0, R.drawable.ic_eye, 0);

                }
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

                if(etReenterPassword.getText().toString().length()>0){
                    etReenterPassword.setCompoundDrawablesWithIntrinsicBounds( R.drawable.password_key, 0, R.drawable.ic_eye, 0);

                }
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
        switch (v.getId()) {
            case R.id.btnChangePassword:
                changePassword();
                break;
            case R.id.btnGotoProfile:
                getActivity().finish();
                break;
        }
    }


    void  changePassword(){
        PasswordRequest passwordRequest=new PasswordRequest();
        Password password=new Password();
        password.setCurrentPassword(etCurrentPassword.getText().toString());
        password.setPassword(etNewPassword.getText().toString());
        passwordRequest.setUser(password);
        String id=  ""+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iPasswordViewPresenter.changePassword(token,id,passwordRequest);
    }
    @Override
    public void getPasswordSuccess(PasswordResponse response) {
        if (response.getSuccess()) {
            llpasswordChangeSuccess.setVisibility(View.VISIBLE);
            llPasswordChangelayout.setVisibility(View.GONE);
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlView, true);

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
