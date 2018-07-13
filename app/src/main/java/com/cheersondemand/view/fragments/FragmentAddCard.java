package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.util.FlipAnimation;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddCard extends Fragment {


    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_member_name)
    TextView tvMemberName;
    @BindView(R.id.tv_validity)
    TextView tvValidity;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.ivType)
    ImageView ivType;
    @BindView(R.id.rl_card_front)
    RelativeLayout rlCardFront;
    @BindView(R.id.tv_cvv)
    TextView tvCvv;
    @BindView(R.id.rl_card_back)
    RelativeLayout rlCardBack;
    @BindView(R.id.rlMain)
    RelativeLayout rlMain;
    @BindView(R.id.etCardNumber)
    EditText etCardNumber;
    @BindView(R.id.etCardHolderName)
    EditText etCardHolderName;
    @BindView(R.id.etExpire)
    EditText etExpire;
    @BindView(R.id.etCvv)
    EditText etCvv;
    @BindView(R.id.btnSaveAdd)
    Button btnSaveAdd;
    Unbinder unbinder;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    int type;
    private boolean isDelete;
    public FragmentAddCard() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(getString(R.string.new_card));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    void init(){
        etExpire.setFilters(new InputFilter[] { filter });

        etCvv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    flipCard();
                }
                else {
                    flipCard();
                }
            }
        });

        etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>4 &&editable.toString().length()<=8){

                    tvCardNumber.setText(etCardNumber.getText().toString().substring(0,4)+"\t"+etCardNumber.getText().toString().substring(4));
                }
                if(editable.toString().length()>8 &&editable.toString().length()<=12){
                    tvCardNumber.setText(etCardNumber.getText().toString().substring(0,4)+"\t"+etCardNumber.getText().toString().substring(4,8)+"\t"+etCardNumber.getText().toString().substring(8));
                }
                if(editable.toString().length()>12 &&editable.toString().length()<=16){
                    tvCardNumber.setText(etCardNumber.getText().toString().substring(0,4)+"\t"+
                            etCardNumber.getText().toString().substring(4,8)+"\t"+
                            etCardNumber.getText().toString().substring(8,12)+"\t"+etCardNumber.getText().toString().substring(12)
                    );
                }

                tvCardNumber.setText(etCardNumber.getText().toString());
                if(editable.toString().length()==5) {
                    type = Util.getCardType(editable.toString());

                }
                else if(editable.toString().length()==14){
                    ivType.setImageResource(android.R.color.transparent);

                }
                else if(editable.toString().length()==15){
                    if(type==Util.AMEX){
                    Util.setCardType(type,ivType,getActivity());
                    }
                    else {
                        ivType.setImageResource(android.R.color.transparent);
                    }

                }
                else if(editable.toString().length()==16) {
                    if(type==Util.AMEX) {
                        ivType.setImageResource(android.R.color.transparent);

                    }
                    else {
                        Util.setCardType(type,ivType,getActivity());

                    }

                    }
                }
        });
        etCardHolderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(tvMemberName!=null)
                {
                    if (TextUtils.isEmpty(editable.toString().trim()))
                        tvMemberName.setText("");
                    else
                        tvMemberName.setText(editable.toString());

                }
            }
        });

        etExpire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int before, int i2) {
                if(before==0)
                    isDelete=false;
                else
                    isDelete=true;
            }

            @Override
            public void afterTextChanged(Editable s) {


                    String source = s.toString();
                    int length = source.length();

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(source);

                    if (length > 0 && length == 3) {
                        if (isDelete)
                            stringBuilder.deleteCharAt(length - 1);
                        else
                            stringBuilder.insert(length - 1, "/");

                        etExpire.setText(stringBuilder);
                        etExpire.setSelection(etExpire.getText().length());

                        // Log.d("test"+s.toString(), "afterTextChanged: append "+length);
                    }

                    if (tvValidity != null) {
                        if (length == 0)
                            tvValidity.setText("");
                        else
                            tvValidity.setText(stringBuilder);
                    }

                }


        });

        etCvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tvCvv != null) {
                    if (TextUtils.isEmpty(editable.toString().trim()))
                        tvCvv.setText("");
                    else
                        tvCvv.setText(editable.toString());

                }
            }
        });

    }

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if ((etExpire.getText().toString() != null &&etExpire.getText().toString().length()==5) ) {
                return "";

            }
            if ((etExpire.getText().toString() != null &&!etExpire.getText().toString().equals("") )&&(etExpire.getText().toString().startsWith("1") || etExpire.getText().toString().startsWith("0")) ) {
                return source.toString();

            }
          else   if (source != null && source.toString().startsWith("1") || source.toString().startsWith("0") ) {
                return source.toString();
            }
            return "";
        }
    };
    private void flipCard()
    {


        FlipAnimation flipAnimation = new FlipAnimation(rlCardFront, rlCardBack);

        if (rlCardFront.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rlMain.startAnimation(flipAnimation);
    }

}
