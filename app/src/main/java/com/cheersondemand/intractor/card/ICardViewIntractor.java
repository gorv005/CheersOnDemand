package com.cheersondemand.intractor.card;

import android.content.Context;

import com.cheersondemand.model.card.AddCardRequest;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.model.card.DeleteCardRequest;

/**
 * Created by AB on 7/31/2017.
 */

public interface ICardViewIntractor {
    interface OnFinishedListener {
        void onSuccessCardList(CardListResponse response);
        void onSuccessAddCard(CardAddResponse response);

        void onError(String response);
        Context getAPPContext();
    }
    public void deleteCard(String token, String userId, DeleteCardRequest deleteCardRequest, OnFinishedListener listener);

    public void addCard(String token, String userId, AddCardRequest addCardRequest, OnFinishedListener listener);
    public void getCardList(String token, String userId, OnFinishedListener listener);



}
