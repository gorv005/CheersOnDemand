package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.cheersondemand.util.viewpager.CircleIndicator;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.card.AdapterCardPayment;
import com.cheersondemand.view.adapter.cart.AdapterProductAmount;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.List;

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
    @BindView(R.id.custom_indicator)
    CircleIndicator customIndicator;
    @BindView(R.id.llIndicatore)
    LinearLayout llIndicatore;


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
        //  cartProduct = (CartProduct) getActivity().getIntent().getExtras().getSerializable(C.CART_DATA);

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
        customIndicator.setViewPager(rvCardList);
        llAddNewAddress.setOnClickListener(this);
        llEdit.setOnClickListener(this);
        btnProceedToPay.setOnClickListener(this);
        tvAddCard.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCardList();
        ActivityContainer.tvTitle.setText(getString(R.string.payment));
    }


    void getCartList() {
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

        if (order_id != null && !order_id.equals("0")) {
            if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
                String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

                iOrderViewPresenterPresenter.getCartList(id, order_id, Util.id(getActivity()));
            } else {
                String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
                String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                iOrderViewPresenterPresenter.getCartList(token, id, order_id, Util.id(getActivity()));
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
        if (response.getSuccess()) {
            if (response.getData() != null && response.getData().size() > 0) {

                cardList = response.getData();

                List<CardList> cardLists = SharedPreference.getInstance(getActivity()).getCard(C.CARD_DATA);
                if (cardLists != null) {
                    for (int i = cardLists.size(); i >= 0; i--) {
                        cardList.set(i, cardLists.get(i-1));
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
                getCartList();
            } else {
                // tvNoCardAvailable.setVisibility(View.VISIBLE);
            }
        } else {
            // util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

        }
    }


    void doPayment() {
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCartId(order_id);
        paymentRequest.setIsGift(checkbox.isChecked());
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
        tvTaxes.setText(getString(R.string.doller) + "0.0");
        tvDelieveryCharges.setText(getString(R.string.doller) + "0.0");
        tvTotalOrder.setText(getString(R.string.doller) + "" + Util.get2Decimal(cartProduct.getOrder().getTotal()));

        tvSubTotal.setText(getString(R.string.doller) + Util.get2Decimal(cartProduct.getOrder().getSubTotal()));
        if (cartProduct.getOrder().getCoupon() != null && cartProduct.getOrder().getAppliedDiscount() > 0) {
            tvCouponAmount.setText("-" + getString(R.string.doller) + cartProduct.getOrder().getAppliedDiscount());
        } else {
            rlCoupenApplied.setVisibility(View.GONE);
        }
        isDetailShown = false;
        lvCharges.setVisibility(View.GONE);

        if (cartProduct.getOrder().getDeliveryAddress() != null) {
            Order order = cartProduct.getOrder();
            tvName.setText(order.getDeliveryAddress().getName());
            tvSubAddress1.setText(order.getDeliveryAddress().getFlatNo() + " " + order.getDeliveryAddress().getAddressFirst());
            tvSubAddress2.setText(order.getDeliveryAddress().getAddressSecond() + " " + order.getDeliveryAddress().getZipCode());
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
        if (response.getSuccess()) {
            cartProduct = response.getData();
            fillDetails();

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
        if (response.getSuccess()) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(C.PAYMENT_RESULT, C.PAYMENT_SUCCESS);
            ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_PAYMENT_RESULT, bundle2);
        }
    }

    @Override
    public void getPaymentError(String response) {
        Bundle bundle2 = new Bundle();
        bundle2.putString(C.PAYMENT_RESULT, C.PAYMENT_FAILED);
        ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_PAYMENT_RESULT, bundle2);
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
                ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_ADD_ADDRESS, bundle1);
                break;
            case R.id.llEdit:
                Bundle bundle2 = new Bundle();
                bundle2.putInt(C.ADDRESS_ID, cartProduct.getOrder().getDeliveryAddress().getId());
                ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_SELECT_ADDRESS, bundle2);
                break;
            case R.id.tvAddCard:
                Bundle bundle3 = new Bundle();

                bundle3.putBoolean(C.IS_FROM_CHECKOUT, true);
                ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_ADD_CARD, bundle3);
                break;
            case R.id.btnProceedToPay:
                doPayment();
                break;
        }
    }
}
