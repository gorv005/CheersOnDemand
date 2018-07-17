package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardList;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.presenter.card.CardViewPresenterImpl;
import com.cheersondemand.presenter.card.ICardViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.card.AdapterCardPayment;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPaymentConfirmation extends Fragment implements ICardViewPresenter.ICardView{


    @BindView(R.id.tvAddCard)
    TextView tvAddCard;
    @BindView(R.id.rvCardList)
    RecyclerViewPager rvCardList;
    Unbinder unbinder;
    AdapterCardPayment adapterCardPayment;
    ICardViewPresenter iCardViewPresenter;
    Util util;
    List<CardList> cardList;
    public FragmentPaymentConfirmation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iCardViewPresenter = new CardViewPresenterImpl(this, getActivity());
        util = new Util();
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
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        rvCardList.setLayoutManager(layout);

    }

    @Override
    public void onResume() {
        super.onResume();
        getCardList();
        ActivityContainer.tvTitle.setText(getString(R.string.payment));
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
                adapterCardPayment = new AdapterCardPayment(cardList, getActivity());
                rvCardList.setAdapter(adapterCardPayment);
            }
            else {
               // tvNoCardAvailable.setVisibility(View.VISIBLE);
            }
        } else {
           // util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

        }
    }

    @Override
    public void onSuccessAddCard(CardAddResponse response) {

    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
