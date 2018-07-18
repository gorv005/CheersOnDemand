package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardList;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.model.card.DeleteCardRequest;
import com.cheersondemand.presenter.card.CardViewPresenterImpl;
import com.cheersondemand.presenter.card.ICardViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.card.AdapterCard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCardList extends Fragment implements ICardViewPresenter.ICardView, View.OnClickListener {


    @BindView(R.id.rvCardList)
    RecyclerView rvCardList;
    @BindView(R.id.LLView)
    RelativeLayout LLView;
    Unbinder unbinder;
    ICardViewPresenter iCardViewPresenter;
    Util util;
    LinearLayoutManager layoutManager;
    AdapterCard adapterCard;
    List<CardList> cardList;
    @BindView(R.id.btnAddNewCard)
    Button btnAddNewCard;
    @BindView(R.id.tvNoCardAvailable)
    TextView tvNoCardAvailable;
    private int posItem;

    public FragmentCardList() {
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
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(getString(R.string.payment_info));
        getCardList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddNewCard.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCardList.setLayoutManager(layoutManager);
        rvCardList.setHasFixedSize(true);
    }

    void getCardList() {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iCardViewPresenter.getCardList(token, id);
    }


    public void deleteCard(int pos) {
        posItem = pos;
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();

        DeleteCardRequest deleteCardRequest = new DeleteCardRequest();
        deleteCardRequest.setCardId(cardList.get(posItem).getCardId());
        iCardViewPresenter.deleteCard(token, id, deleteCardRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessCardList(CardListResponse response) {
        if (response.getSuccess()) {
            if (response.getData() != null && response.getData().size() > 0) {
                tvNoCardAvailable.setVisibility(View.GONE);

                cardList = response.getData();
                adapterCard = new AdapterCard(cardList, getActivity());
                rvCardList.setAdapter(adapterCard);
            }
            else {
                tvNoCardAvailable.setVisibility(View.VISIBLE);
            }
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

        }
    }

    @Override
    public void onSuccessAddCard(CardAddResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, false);

            cardList.remove(posItem);
            adapterCard.notifyDataSetChanged();

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNewCard:
                Bundle bundle3=new Bundle();
                bundle3.putBoolean(C.IS_FROM_CHECKOUT,false);
                ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_ADD_CARD, bundle3);
                break;
        }
    }
}
