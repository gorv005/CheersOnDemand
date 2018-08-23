package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.changepassword.PasswordResponse;
import com.cheersondemand.model.changepassword.ResetPassword;
import com.cheersondemand.model.changepassword.ResetPasswordRequest;
import com.cheersondemand.presenter.password.IPasswordViewPresenter;
import com.cheersondemand.presenter.password.PasswordViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.CustomEditText;
import com.cheersondemand.util.DrawableClickListener;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.MainActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentResetPassword extends Fragment implements IPasswordViewPresenter.IPasswordView,View.OnClickListener{


    @BindView(R.id.symbol)
    ImageView symbol;
    @BindView(R.id.btnLoginTab)
    Button btnLoginTab;
    @BindView(R.id.viewSignUp)
    RelativeLayout viewSignUp;
    @BindView(R.id.etNewPassword)
    CustomEditText etNewPassword;
    @BindView(R.id.etConfirmNewPassword)
    CustomEditText etConfirmNewPassword;
    @BindView(R.id.btnResetPassword)
    CircularProgressButton btnResetPassword;
    @BindView(R.id.viewB)
    LinearLayout viewB;
    @BindView(R.id.LLView)
    LinearLayout LLView;
    Unbinder unbinder;
    Util util;
    IPasswordViewPresenter iPasswordViewPresenter;
    String token;
    boolean isPasswordVisibleConfirm=false,isPasswordVisibleNew=false;
    public FragmentResetPassword() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iPasswordViewPresenter=new PasswordViewPresenterImpl(this,getActivity());
        util=new Util();
        token = getArguments().getString(C.TOKEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        Log.e("DEBUG","token=="+token);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnResetPassword.setOnClickListener(this);
        initFields();
    }

    void resetPassword(){
        Util.hideKeyboard(getActivity());
        ResetPasswordRequest passwordRequest=new ResetPasswordRequest();
        ResetPassword password=new ResetPassword();
        password.setPassword(etNewPassword.getText().toString());
        password.setResetPasswordToken(token);

        passwordRequest.setUser(password);
        iPasswordViewPresenter.resetPassword(passwordRequest);
    }





    void initFields() {


        btnResetPassword.setEnabled(false);
        etNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                try {
                    if (b) {
                        etNewPassword.setBackgroundResource(R.drawable.edit_text_back_enable);


                    } else {
                        etNewPassword.setBackgroundResource(R.drawable.edit_text_back_disable);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        etConfirmNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                try {
                    if (b) {
                        etConfirmNewPassword.setBackgroundResource(R.drawable.edit_text_back_enable);


                    } else {
                        etConfirmNewPassword.setBackgroundResource(R.drawable.edit_text_back_disable);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                            etNewPassword.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etNewPassword.setCompoundDrawablesWithIntrinsicBounds( R.drawable.password_key, 0, R.drawable.ic_eye_visible, 0);

                        }
                        else {
                            isPasswordVisibleNew=false;
                            etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etNewPassword.setCompoundDrawablesWithIntrinsicBounds( R.drawable.password_key, 0, R.drawable.ic_eye, 0);

                        }
                        break;

                    default:
                        break;
                }
            }

        });
        etConfirmNewPassword.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        if(!isPasswordVisibleConfirm) {
                            isPasswordVisibleConfirm=true;
                            etConfirmNewPassword.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etConfirmNewPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password_key, 0, R.drawable.ic_eye_visible, 0);

                        }
                        else {
                            isPasswordVisibleConfirm=false;
                            etConfirmNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etConfirmNewPassword.setCompoundDrawablesWithIntrinsicBounds( R.drawable.password_key, 0, R.drawable.ic_eye, 0);

                        }
                        break;

                    default:
                        break;
                }
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
                else {
                    etNewPassword.setCompoundDrawablesWithIntrinsicBounds( R.drawable.password_key, 0, 0, 0);

                }

                validationFields();


            }
        });
        etConfirmNewPassword.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if(etConfirmNewPassword.getText().toString().length()>0){
                    etConfirmNewPassword.setCompoundDrawablesWithIntrinsicBounds( R.drawable.password_key, 0, R.drawable.ic_eye, 0);

                }
                else {
                    etConfirmNewPassword.setCompoundDrawablesWithIntrinsicBounds( R.drawable.password_key, 0, 0, 0);

                }
                validationFields();


            }
        });


    }

    void validationFields() {

            if (etNewPassword.length() > 4 && etNewPassword.length() < 31) {

                if (etConfirmNewPassword.length() > 4 && etConfirmNewPassword.length() < 31) {
                    if(etNewPassword.getText().toString().equals(etConfirmNewPassword.getText().toString())) {
                        btnResetPassword.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                        btnResetPassword.setEnabled(true);
                    }
                    else {
                        btnResetPassword.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                        btnResetPassword.setEnabled(false);
                    }
                } else {

                    btnResetPassword.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                    btnResetPassword.setEnabled(false);

                }

            } else {

                btnResetPassword.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                btnResetPassword.setEnabled(false);

            }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getPasswordSuccess(PasswordResponse response) {
        try {
            if (response.getSuccess()) {
                 util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Bundle bundle = new Bundle();
                        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_AUTHNITICATION);
                        bundle.putBoolean(C.IS_LOGIN_SCREEN, true);
                        bundle.putBoolean(C.IS_FROM_HOME, false);
                        intent.putExtra(C.BUNDLE, bundle);
                        startActivity(intent);
                    }
                }, 2000);
            }
            else {
                if(response.getErrors()!=null && response.getErrors().size()>=0){
                    util.setSnackbarMessage(getActivity(), response.getErrors().get(0).getField(), LLView, true);

                }
                else {
                    util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, LLView, true);

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG,getActivity());

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnResetPassword:
                resetPassword();
                break;
        }
    }
}
