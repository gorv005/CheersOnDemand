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
import com.cheersondemand.model.card.CardList;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    @BindView(R.id.etPincode)
    EditText etPincode;
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
    ICardViewPresenter iCardViewPresenter;
    Util util;
    boolean isCheckout, isRetryPayment = false;
    private boolean isDelete;
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if ((etExpire.getText().toString() != null && etExpire.getText().toString().length() == 5)) {
                return "";

            } else if ((etExpire.getText().toString() != null && etExpire.getText().toString().length() == 1)) {
                if ((etExpire.getText().toString() != null && !etExpire.getText().toString().equals("")) && (etExpire.getText().toString().startsWith("1"))) {
                    if (source.toString().equals("0") || source.toString().equals("1") || source.toString().equals("2")) {
                        return source.toString();
                    } else {
                        return "";
                    }
                }
                return source.toString();

            } else if ((etExpire.getText().toString() != null && !etExpire.getText().toString().equals("")) && (etExpire.getText().toString().startsWith("1") || etExpire.getText().toString().startsWith("0"))) {
                return source.toString();

            } else if (source != null && source.toString().startsWith("1") || source.toString().startsWith("0")) {
                return source.toString();
            }
            return "";
        }
    };

    public FragmentAddCard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iCardViewPresenter = new CardViewPresenterImpl(this, getActivity());
        util = new Util();
        isCheckout = getArguments().getBoolean(C.IS_FROM_CHECKOUT);
        try {
            isRetryPayment = getArguments().getBoolean(C.IS_RETRY_PAYEMNT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((ActivityContainer) getActivity()).setTitle(getString(R.string.new_card));

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
        if (isCheckout) {
            rlISDetailSave.setVisibility(VISIBLE);
        } else {
            rlISDetailSave.setVisibility(GONE);
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
        addCardRequest.setZipcode(etPincode.getText().toString());
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
               /* if (editable.toString().length() > 4 && editable.toString().length() <= 8) {

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
*/
                if (etCardNumber.getText().toString().length() > 4) {
                    tvCardNumber.setText(Util.handleCardNumber(etCardNumber.getText().toString(), " "));
                } else {
                    tvCardNumber.setText(etCardNumber.getText().toString());
                }

                //  tvCardNumber.setText(etCardNumber.getText().toString());
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

    }

    private void flipCard() {


        try {
            FlipAnimation flipAnimation = new FlipAnimation(rlCardFront, rlCardBack);

            if (rlCardFront.getVisibility() == GONE) {
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
                    if (etPincode.getText().length() >= 1) {
                        if (etCvv.getText().length() == 3) {
                            btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                            btnSaveAdd.setEnabled(true);
                        } else {
                            disableButton();
                        }

                    } else {
                        disableButton();
                    }
                } else {
                    disableButton();
                }
            } else {

                disableButton();

            }
        } else {

            disableButton();

        }


    }

    void disableButton() {
        btnSaveAdd.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
        btnSaveAdd.setEnabled(false);
    }

    private void addCardToStripe(String cardNumber, String month, String year, String cvv) {
        showProgress();
        Card stripeCard = new Card(cardNumber, Integer.parseInt(month), Integer.parseInt(year), null);
        stripeCard.setName(etCardHolderName.getText().toString());
        stripeCard.setAddressZip(etPincode.getText().toString());
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
                if (isCheckout) {
                    if (checkboxISSave.isChecked()) {
                        addCard(token.getId());
                    } else {
                        CardList cardList = new CardList();
                        cardList.setCardId(null);
                        cardList.setCardHolderName(etCardHolderName.getText().toString());
                        cardList.setZipCode(etPincode.getText().toString());
                        cardList.setExpMonth(Integer.parseInt(etExpire.getText().toString().split("/")[0]));
                        cardList.setExpYear(Integer.parseInt(etExpire.getText().toString().split("/")[1]));
                        cardList.setLast4(etCardNumber.getText().toString().substring(etCardNumber.getText().toString().length() - 4));
                        cardList.setCardNumber(etCardNumber.getText().toString());
                        cardList.setStripeToken(token.getId());
                        cardList.setBrand(Util.getCardTypeUsingBrandName(type));
                        cardList.setIsDefaultSource(true);
                        SharedPreference.getInstance(getActivity()).addCard(C.CARD_DATA, cardList);
                        gotoPaymentScreen();

                    }
                } else {
                    addCard(token.getId());

                }
            }
        });
    }

    @Override
    public void onSuccessCardList(CardListResponse response) {

    }

    @Override
    public void onSuccessAddCard(CardAddResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llBack, false);
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        gotoPaymentScreen();
                    }
                }, 2000);


            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llBack, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void gotoPaymentScreen() {
        if (isCheckout) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(C.IS_RETRY_PAYEMNT, isRetryPayment);
            ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_PAYMENT_CONFIRMATION, bundle);
        } else {
            getActivity().onBackPressed();
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
                try {
                    Util.hideKeyboard(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String a[] = etExpire.getText().toString().split("/");
                addCardToStripe(etCardNumber.getText().toString(), a[0], a[1], tvCvv.getText().toString());
                break;
        }
    }
}
