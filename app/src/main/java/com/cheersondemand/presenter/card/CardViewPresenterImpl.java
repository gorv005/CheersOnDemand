package com.cheersondemand.presenter.card;

import android.content.Context;

import com.cheersondemand.intractor.card.CardViewIntractorImpl;
import com.cheersondemand.intractor.card.ICardViewIntractor;
import com.cheersondemand.model.card.AddCardRequest;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.model.card.DeleteCardRequest;


public class CardViewPresenterImpl implements ICardViewPresenter, ICardViewIntractor.OnFinishedListener {

    ICardView mView;
    Context context;
    ICardViewIntractor iCardViewIntractor;

    public CardViewPresenterImpl(ICardView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iCardViewIntractor = new CardViewIntractorImpl();

    }
    @Override
    public void onSuccessCardList(CardListResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onSuccessCardList(response);
        }
    }

    @Override
    public void onSuccessAddCard(CardAddResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onSuccessAddCard(response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }


    @Override
    public void addCard(String token, String userId, AddCardRequest addCardRequest) {
        if (mView != null) {
            mView.showProgress();

            iCardViewIntractor.addCard(token,userId,addCardRequest, this);
        }
    }

    @Override
    public void getCardList(String token, String userId) {
        if (mView != null) {
            mView.showProgress();

            iCardViewIntractor.getCardList(token,userId, this);
        }
    }

    @Override
    public void deleteCard(String token, String userId, DeleteCardRequest deleteCardRequest) {
        if (mView != null) {
            mView.showProgress();
            iCardViewIntractor.deleteCard(token,userId,deleteCardRequest, this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
