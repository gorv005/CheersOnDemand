package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardList;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.addtocart.CartProduct;
import com.cheersondemand.model.order.addtocart.Order;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.payment.PaymentRequest;
import com.cheersondemand.model.payment.PaymentResponse;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.card.CardViewPresenterImpl;
import com.cheersondemand.presenter.card.ICardViewPresenter;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.presenter.payment.IPaymentViewPresenter;
import com.cheersondemand.presenter.payment.PaymentViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.NonScrollListView;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.card.AdapterCardPayment;
import com.cheersondemand.view.adapter.cart.AdapterProductAmount;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPaymentConfirmation extends Fragment implements ICardViewPresenter.ICardView, View.OnClickListener, IPaymentViewPresenter.IPaymentView, IOrderViewPresenterPresenter.IOrderView {


    @BindView(R.id.tvAddCard)
    TextView tvAddCard;
    @BindView(R.id.rvCardList)
    RecyclerViewPager rvCardList;
    Unbinder unbinder;
    AdapterCardPayment adapterCardPayment;
    ICardViewPresenter iCardViewPresenter;
    Util util;
    List<CardList> cardList;
    CartProduct cartProduct;
    @BindView(R.id.tvNoOfItems)
    TextView tvNoOfItems;
    @BindView(R.id.tvViewDetail)
    TextView tvViewDetail;
    @BindView(R.id.lvCharges)
    NonScrollListView lvCharges;
    @BindView(R.id.tvTotalOrder)
    TextView tvTotalOrder;
    @BindView(R.id.tvTaxes)
    TextView tvTaxes;
    @BindView(R.id.tvDelieveryCharges)
    TextView tvDelieveryCharges;
    @BindView(R.id.tvCouponAmount)
    TextView tvCouponAmount;
    @BindView(R.id.tvApplyCoupon)
    TextView tvApplyCoupon;
    @BindView(R.id.tvSubTotal)
    TextView tvSubTotal;
    @BindView(R.id.rlApplyCoupon)
    RelativeLayout rlApplyCoupon;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.rlStoreItem)
    RelativeLayout rlStoreItem;
    @BindView(R.id.location)
    RelativeLayout location;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.rlAddName)
    RelativeLayout rlAddName;

    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.add)
    RelativeLayout add;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.llEdit)
    LinearLayout llEdit;
    @BindView(R.id.llAddNewAddress)
    LinearLayout llAddNewAddress;
    @BindView(R.id.llModify)
    LinearLayout llModify;
    @BindView(R.id.llAddressItem)
    RelativeLayout llAddressItem;
    AdapterProductAmount adapterProductAmount;
    @BindView(R.id.rlCoupenApplied)
    RelativeLayout rlCoupenApplied;
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;
    IPaymentViewPresenter iPaymentViewPresenter;
    boolean isDetailShown = false;
    @BindView(R.id.tvSubAddress1)
    TextView tvSubAddress1;
    @BindView(R.id.tvSubAddress2)
    TextView tvSubAddress2;
    @BindView(R.id.btnProceedToPay)
    Button btnProceedToPay;
    @BindView(R.id.llButton)
    RelativeLayout llButton;

    @BindView(R.id.llIndicatore)
    LinearLayout llIndicatore;
    @BindView(R.id.tvTotalAmount)
    TextView tvTotalAmount;
    @BindView(R.id.rlTotalAmount)
    RelativeLayout rlTotalAmount;
    @BindView(R.id.tvScheduleTime)
    TextView tvScheduleTime;
    @BindView(R.id.rlSchedileTime)
    LinearLayout rlSchedileTime;
    private SwitchDateTimeDialogFragment dateTimeFragment;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    private int dotsCount;
    private ImageView[] dots;
    Date mCurrentDate;
    boolean isScheduleTimeselected=false;
    Order order;
    boolean isRetryPayment=false;
    public FragmentPaymentConfirmation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iCardViewPresenter = new CardViewPresenterImpl(this, getActivity());
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());
        iPaymentViewPresenter = new PaymentViewPresenterImpl(this, getActivity());
        util = new Util();
        isRetryPayment= getArguments().getBoolean(C.IS_RETRY_PAYEMNT);
        if(isRetryPayment) {
            order = (Order) getArguments().getSerializable(C.ORDER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_confirmation, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvCardList.setLayoutManager(layout);
        tvViewDetail.setOnClickListener(this);
        //    customIndicator.setViewPager(rvCardList);
        llAddNewAddress.setOnClickListener(this);
        llEdit.setOnClickListener(this);
        btnProceedToPay.setOnClickListener(this);
        rlSchedileTime.setOnClickListener(this);
        tvAddCard.setOnClickListener(this);
        SharedPreference.getInstance(getActivity()).setBoolean(C.IS_FROM_PAYMENT, true);
        rvCardList.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int i1, int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

            }
        });

        initDateTimePicker();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ActivityContainer)getActivity()).setTitle(getString(R.string.payment));
        getCardList();
    }


    void initDateTimePicker(){
        mCurrentDate=Util.getDefaultDate();
        // Construct SwitchDateTimePicker
        dateTimeFragment = (SwitchDateTimeDialogFragment)getActivity().getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.scheduleTime),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
                  //  getString(R.string.clean) // Optional
            );
        }

        // Optionally define a timezone
        dateTimeFragment.setTimeZone(TimeZone.getDefault());

        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setHighlightAMPMSelection(true);
        dateTimeFragment.setMinimumDateTime(Util.getCurrentDate());
        dateTimeFragment.setMaximumDateTime(Util.get1monthLaterDate());
        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e("DEBUG", e.getMessage());
        }

        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                mCurrentDate=date;
                tvScheduleTime.setText(myDateFormat.format(date));
                isScheduleTimeselected=true;
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
               // tvScheduleTime.setText("");
            }
        });
    }

    void setDefaultDateTime(){
        if(tvScheduleTime.getText().toString().equals(getString(R.string.scheduleTime))){
            mCurrentDate=Util.getDefaultDate();
        }
        else {
            if(mCurrentDate.getTime()<Util.getDefaultDate().getTime() ||mCurrentDate.getTime()>Util.get1monthLaterDate().getTime()){
                mCurrentDate=Util.getDefaultDate();

            }
        }
        dateTimeFragment.startAtCalendarView();
        dateTimeFragment.setDefaultDateTime(mCurrentDate);
        dateTimeFragment.show(getActivity().getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
    }
    private void setUiPageViewController() {

        dotsCount = adapterCardPayment.getItemCount();
        dots = new ImageView[dotsCount];
        llIndicatore.removeAllViews();
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            llIndicatore.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    void getCartList() {
        String order_id=null;
        if(isRetryPayment){
            order_id=""+order.getId();
        }
        else {
            order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);
        }
        if (order_id != null && !order_id.equals("0")) {
            if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
                String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

                iOrderViewPresenterPresenter.getCartList(id, order_id, Util.id(getActivity()), false);
            } else {
                String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
                String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                iOrderViewPresenterPresenter.getCartList(token, id, order_id, Util.id(getActivity()), false);
            }
        }
    }

    void getCardList() {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iCardViewPresenter.getCardList(token, id);
    }

    @Override
    public void onSuccessCardList(CardListResponse response) {
        try {
            if (response.getSuccess()) {

                cardList=new ArrayList<>();
                    List<CardList> cardLists = SharedPreference.getInstance(getActivity()).getCard(C.CARD_DATA);
                    if (cardLists != null && cardLists.size()>0) {
                        for (int i = cardLists.size(); i > 0; i--) {
                            cardList.add(i-1, cardLists.get(i - 1));
                        }
                    }
                if(response.getData()!=null && response.getData().size()>0) {
                        for(int i=0;i<response.getData().size();i++) {
                            cardList.add(response.getData().get(i));
                        }
                }
                else {
                        if(isRetryPayment){
                            gotoCard();
                        }
                }

                    adapterCardPayment = new AdapterCardPayment(cardList, getActivity());
                    rvCardList.setAdapter(adapterCardPayment);
                    for (int j = 0; j < cardList.size(); j++) {
                        if (cardList.get(j).getIsDefaultSource()) {
                            rvCardList.scrollToPosition(j);
                            break;

                        }
                    }
                    setUiPageViewController();
                    getCartList();

            } else {
                // util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);
                getCartList();

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void gotoCard(){
        Bundle bundle3 = new Bundle();

        bundle3.putBoolean(C.IS_FROM_CHECKOUT, true);
        bundle3.putBoolean(C.IS_RETRY_PAYEMNT, true);
        ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_ADD_CARD, bundle3);
    }
    void retryPayment() {
        if(cardList!=null && cardList.size()>0) {
            String order_id = "" + order.getId();
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setIsGift(checkbox.isChecked());
            if (isScheduleTimeselected) {
                paymentRequest.setScheduleTime(tvScheduleTime.getText().toString());
            }
            if (cardList.get(rvCardList.getCurrentPosition()).getCardId() != null) {
                paymentRequest.setSource(cardList.get(rvCardList.getCurrentPosition()).getCardId());
            } else {
                paymentRequest.setSource(cardList.get(rvCardList.getCurrentPosition()).getStripeToken());
            }
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iPaymentViewPresenter.retryPayment(token, id, order_id, paymentRequest);
        }
    }

    void doPayment() {

        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCartId(order_id);
        paymentRequest.setIsGift(checkbox.isChecked());
        if(isScheduleTimeselected){
            paymentRequest.setScheduleTime(tvScheduleTime.getText().toString());
        }
        if (cardList.get(rvCardList.getCurrentPosition()).getCardId() != null) {
            paymentRequest.setCardId(cardList.get(rvCardList.getCurrentPosition()).getCardId());
        } else {
            paymentRequest.setStripeToken(cardList.get(rvCardList.getCurrentPosition()).getStripeToken());
        }
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iPaymentViewPresenter.orderPayment(token, paymentRequest);
    }

    void fillDetails() {
        adapterProductAmount = new AdapterProductAmount(getActivity(), cartProduct.getOrder().getOrderItems());
        lvCharges.setAdapter(adapterProductAmount);
        tvNoOfItems.setText(cartProduct.getOrder().getOrderItems().size()+" Items");
        tvTaxes.setText(getString(R.string.doller) + cartProduct.getOrder().getTax());
        tvDelieveryCharges.setText(getString(R.string.doller) + "0.0");
        tvTotalOrder.setText(getString(R.string.doller) + "" + Util.get2Decimal(cartProduct.getOrder().getSubTotal()));
        tvSubTotal.setText(getString(R.string.doller) + Util.get2Decimal(cartProduct.getOrder().getTotal()));
        if (cartProduct.getOrder().getCoupon() != null && cartProduct.getOrder().getAppliedDiscount() > 0) {
            tvCouponAmount.setText("-" + getString(R.string.doller) + cartProduct.getOrder().getAppliedDiscount());
            rlCoupenApplied.setVisibility(View.VISIBLE);
            double am=cartProduct.getOrder().getSubTotal()-cartProduct.getOrder().getAppliedDiscount();
            tvTotalAmount.setText(getString(R.string.doller) + Util.get2Decimal(am));
            rlTotalAmount.setVisibility(View.VISIBLE);

        } else {
            rlTotalAmount.setVisibility(View.GONE);
            rlCoupenApplied.setVisibility(View.GONE);
        }
        isDetailShown = false;
        lvCharges.setVisibility(View.GONE);

        if (cartProduct.getOrder().getDeliveryAddress() != null) {
            Order order = cartProduct.getOrder();
            tvName.setText(order.getDeliveryAddress().getName());
            tvSubAddress1.setText(order.getDeliveryAddress().getFlatNo());
            tvSubAddress2.setText(order.getDeliveryAddress().getAddress() + " " + order.getDeliveryAddress().getZipCode());
            tvPhone.setText(" " + order.getDeliveryAddress().getPhoneNumber());
        }
    }

    @Override
    public void onSuccessAddCard(CardAddResponse response) {

    }

    @Override
    public void getCreateOrderSuccess(CreateOrderResponse response) {

    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {

    }

    @Override
    public void getUpdateCartSuccess(UpdateCartResponse response) {

    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {

    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {
        try {
            if (response.getSuccess()) {
                cartProduct = response.getData();
                fillDetails();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {

    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {

    }

    @Override
    public void getWishListSuccess(WishListDataResponse response) {

    }

    @Override
    public void onPaymentSuccess(PaymentResponse response) {
        try {
            if (response.getSuccess()) {
                SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, null);

                Bundle bundle2 = new Bundle();
                bundle2.putString(C.PAYMENT_RESULT, C.PAYMENT_SUCCESS);
                ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_PAYMENT_RESULT, bundle2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPaymentError(String response) {
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putString(C.PAYMENT_RESULT, C.PAYMENT_FAILED);
            ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_PAYMENT_RESULT, bundle2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseError(String response) {

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
            case R.id.tvViewDetail:
                if (isDetailShown) {
                    lvCharges.setVisibility(View.GONE);
                    isDetailShown = false;
                } else {
                    lvCharges.setVisibility(View.VISIBLE);
                    isDetailShown = true;
                }
                break;
            case R.id.llAddNewAddress:
                Bundle bundle1 = new Bundle();

                bundle1.putBoolean(C.IS_EDIT, false);
                bundle1.putBoolean(C.IS_FROM_CHECKOUT, true);
                bundle1.putBoolean(C.IS_RETRY_PAYEMNT, isRetryPayment);
                ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_ADD_ADDRESS_1, bundle1);
                break;
            case R.id.llEdit:
                if(isRetryPayment) {
                   getActivity().onBackPressed();
                }
                else {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(C.ADDRESS_ID, cartProduct.getOrder().getDeliveryAddress().getId());
                    bundle2.putBoolean(C.IS_FROM_CHECKOUT, true);
                    ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_SELECT_ADDRESS, bundle2);
                }
                break;
            case R.id.tvAddCard:
                Bundle bundle3 = new Bundle();

                bundle3.putBoolean(C.IS_FROM_CHECKOUT, true);
                bundle3.putBoolean(C.IS_RETRY_PAYEMNT, isRetryPayment);
                ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_ADD_CARD, bundle3);
                break;
            case R.id.btnProceedToPay:
                if(!isScheduleTimeselected) {
                   paymentCall();
                }
                else {
                    if(mCurrentDate.getTime()>=Util.getCurrentDate().getTime() && mCurrentDate.getTime()<=Util.get1monthLaterDate().getTime()){
                        paymentCall();
                    }
                    else {
                        dialogDateTimeIssue();
                    }
                }
                break;
            case R.id.rlSchedileTime:
               setDefaultDateTime();
                break;
        }
    }


    void  paymentCall(){
        if (isRetryPayment) {
            retryPayment();
        } else {
            doPayment();
        }
    }
    void dialogDateTimeIssue() {
        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.dialog_ok); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message
        TextView title = (TextView) dialog.findViewById(R.id.tvmessagedialogtitle);

        TextView message = (TextView) dialog.findViewById(R.id.tvmessagedialogtext);
        title.setText(getString(R.string.app_name));
        message.setText(getString(R.string.date_time_validation));
//add some action to the buttons
        Button yes = (Button) dialog.findViewById(R.id.bmessageDialogOK);
        yes.setText(getString(R.string.ok));
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
