package com.cheersondemand.presenter.card;

import com.cheersondemand.model.card.AddCardRequest;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.model.card.DeleteCardRequest;

/**
 * Created by AB on 5/30/2018.
 */

public interface ICardViewPresenter {

    public void addCard(String token, String userId, AddCardRequest addCardRequest);
    public void getCardList(String token, String userId);
    public void deleteCard(String token, String userId, DeleteCardRequest deleteCardRequest);

    void onDestroy();

    interface ICardView {

        void onSuccessCardList(CardListResponse response);
        void onSuccessAddCard(CardAddResponse response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
