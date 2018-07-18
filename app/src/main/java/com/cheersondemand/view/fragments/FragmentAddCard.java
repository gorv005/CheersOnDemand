package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.card.AddCardRequest;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.presenter.card.CardViewPresenterImpl;
import com.cheersondemand.presenter.card.ICardViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.FlipAnimation;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddCard extends Fragment implements ICardViewPresenter.ICardView, View.OnClickListener {


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
    @BindView(R.id.checkboxISSave)
    CheckBox checkboxISSave;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.rlISDetailSave)
    RelativeLayout rlISDetailSave;
    private boolean isDelete;
    ICardViewPresenter iCardViewPresenter;
    Util util;

    boolean isCheckout;
    public FragmentAddCard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iCardViewPresenter = new CardViewPresenterImpl(this, getActivity());
        util = new Util();
        isCheckout=getArguments().getBoolean(C.IS_FROM_CHECKOUT);
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
        btnSaveAdd.setOnClickListener(this);
        if(isCheckout){
            rlISDetailSave.setVisibility(View.VISIBLE);
        }
        else {
            rlISDetailSave.setVisibility(View.GONE);
        }
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    void addCard(String stripe_token) {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();

        AddCardRequest addCardRequest = new AddCardRequest();
        addCardRequest.setStripeToken(stripe_token);
        iCardViewPresenter.addCard(token, id, addCardRequest);
    }

    void init() {
        btnSaveAdd.setEnabled(false);

        etExpire.setFilters(new InputFilter[]{filter});

        etCvv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    flipCard();
                } else {
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
                if (editable.toString().length() > 4 && editable.toString().length() <= 8) {

                    tvCardNumber.setText(etCardNumber.getText().toString().substring(0, 4) + "\t" + etCardNumber.getText().toString().substring(4));
                }
                if (editable.toString().length() > 8 && editable.toString().length() <= 12) {
                    tvCardNumber.setText(etCardNumber.getText().toString().substring(0, 4) + "\t" + etCardNumber.getText().toString().substring(4, 8) + "\t" + etCardNumber.getText().toString().substring(8));
                }
                if (editable.toString().length() > 12 && editable.toString().length() <= 16) {
                    tvCardNumber.setText(etCardNumber.getText().toString().substring(0, 4) + "\t" +
                            etCardNumber.getText().toString().substring(4, 8) + "\t" +
                            etCardNumber.getText().toString().substring(8, 12) + "\t" + etCardNumber.getText().toString().substring(12)
                    );
                }

                tvCardNumber.setText(etCardNumber.getText().toString());
                if (editable.toString().length() == 5) {
                    type = Util.getCardType(editable.toString());

                } else if (editable.toString().length() == 14) {
                    ivType.setImageResource(android.R.color.transparent);

                } else if (editable.toString().length() == 15) {
                    if (type == Util.AMEX) {
                        Util.setCardType(type, ivType, getActivity());
                    } else {
                        ivType.setImageResource(android.R.color.transparent);
                    }

                } else if (editable.toString().length() == 16) {
                    if (type == Util.AMEX) {
                        ivType.setImageResource(android.R.color.transparent);

                    } else {
                        Util.setCardType(type, ivType, getActivity());

                    }

                }
                validationFields();
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
                if (tvMemberName != null) {
                    if (TextUtils.isEmpty(editable.toString().trim()))
                        tvMemberName.setText("");
                    else
                        tvMemberName.setText(editable.toString());

                }
                validationFields();
            }
        });

        etExpire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int before, int i2) {
                if (before == 0)
                    isDelete = false;
                else
                    isDelete = true;
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
                validationFields();
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
                validationFields();
            }
        });

    }

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if ((etExpire.getText().toString() != null && etExpire.getText().toString().length() == 5)) {
                return "";

            }
            if ((etExpire.getText().toString() != null && !etExpire.getText().toString().equals("")) && (etExpire.getText().toString().startsWith("1") || etExpire.getText().toString().startsWith("0"))) {
                return source.toString();

            } else if (source != null && source.toString().startsWith("1") || source.toString().startsWith("0")) {
                return source.toString();
            }
            return "";
        }
    };

    private void flipCard() {


        try {
            FlipAnimation flipAnimation = new FlipAnimation(rlCardFront, rlCardBack);

            if (rlCardFront.getVisibility() == View.GONE) {
                flipAnimation.reverse();
            }
            rlMain.startAnimation(flipAnimation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void validationFields() {
        if (etCardNumber.getText().length() >= 14 && etCardNumber.length() <= 16) {

            if (etExpire.getText().length() == 5) {

                if (etCardHolderName.getText().length() >= 1) {
                    if (etCvv.getText().length() == 3) {
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


    }


    private void addCardToStripe(String cardNumber, String month, String year, String cvv) {
        showProgress();
        Card stripeCard = new Card(cardNumber, Integer.parseInt(month), Integer.parseInt(year), null);
        stripeCard.setName(etCardHolderName.getText().toString());
        Stripe stripe = new Stripe(getActivity(), C.STRIPE_APP_KEY);
        stripe.createToken(stripeCard, new TokenCallback() {
            @Override
            public void onError(Exception error) {
                hideProgress();
                util.setSnackbarMessage(getActivity(), error.getLocalizedMessage(), llBack, true);

            }

            @Override
            public void onSuccess(Token token) {
                hideProgress();
                // card.setStripeTokenId(token.getId());
                Log.e("DEBUG", "Stripe token : " + token.getId());
                addCard(token.getId());
            }
        });
    }

    @Override
    public void onSuccessCardList(CardListResponse response) {

    }

    @Override
    public void onSuccessAddCard(CardAddResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), llBack, false);
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 10 seconds
                    getActivity().onBackPressed();
                }
            }, 2000);


        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), llBack, true);

        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, llBack, true);

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveAdd:
                String a[] = etExpire.getText().toString().split("/");
                addCardToStripe(etCardNumber.getText().toString(), a[0], a[1], tvCvv.getText().toString());
                break;
        }
    }
}
