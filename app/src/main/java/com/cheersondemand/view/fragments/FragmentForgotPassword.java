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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.changepassword.Password;
import com.cheersondemand.model.changepassword.PasswordRequest;
import com.cheersondemand.model.changepassword.PasswordResponse;
import com.cheersondemand.presenter.password.IPasswordViewPresenter;
import com.cheersondemand.presenter.password.PasswordViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.Util;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentForgotPassword extends Fragment implements View.OnClickListener,IPasswordViewPresenter.IPasswordView {


    @BindView(R.id.tvSuccessMsg)
    TextView tvSuccessMsg;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.btnSendLink)
    CircularProgressButton btnSendLink;
    @BindView(R.id.viewB)
    LinearLayout viewB;
    @BindView(R.id.tvContinueToLogin)
    TextView tvContinueToLogin;
    @BindView(R.id.LLView)
    LinearLayout LLView;
    Unbinder unbinder;
    Util util;
    IPasswordViewPresenter iPasswordViewPresenter;
    public FragmentForgotPassword() {
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
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSendLink.setEnabled(false);
        tvContinueToLogin.setOnClickListener(this);
        btnSendLink.setOnClickListener(this);
        etEmail.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                validation();



            }
        });
    }

    void  validation(){
            if(Util.isValidMail(etEmail.getText().toString())){


                btnSendLink.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                btnSendLink.setEnabled(true);
            }
            else {

                btnSendLink.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                btnSendLink.setEnabled(false);

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
            case R.id.tvContinueToLogin:
                getActivity().finish();
                break;
            case R.id.btnSendLink:
                forgotPassword();
                break;
        }
    }


    void forgotPassword(){
        Util.hideKeyboard(getActivity());
        PasswordRequest passwordRequest=new PasswordRequest();
        Password password=new Password();
        password.setEmail(etEmail.getText().toString());
        passwordRequest.setUser(password);
        iPasswordViewPresenter.forgotPassword(passwordRequest);
    }
    @Override
    public void getPasswordSuccess(PasswordResponse response) {
        try {
        if (response.getSuccess()) {
                tvSuccessMsg.setText(response.getMessage());
                tvSuccessMsg.setTextColor(ContextCompat.getColor(getActivity(), R.color.green));
            }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

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
        util.showDialog(C.MSG, getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }
}
