package com.cheersondemand.intractor.card;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.card.AddCardRequest;
import com.cheersondemand.model.card.CardAddResponse;
import com.cheersondemand.model.card.CardListResponse;
import com.cheersondemand.model.card.DeleteCardRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class CardViewIntractorImpl implements ICardViewIntractor {


    @Override
    public void deleteCard(String token, String userId, DeleteCardRequest deleteCardRequest, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().deleteCard(new ResponseResolver<CardAddResponse>() {
                @Override
                public void onSuccess(CardAddResponse r, Response response) {
                    listener.onSuccessAddCard(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            CardAddResponse response = gson.fromJson(msg, CardAddResponse.class);
                            listener.onSuccessAddCard(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, userId, deleteCardRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCard(String token, String userId, AddCardRequest addCardRequest, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().addCard(new ResponseResolver<CardAddResponse>() {
                @Override
                public void onSuccess(CardAddResponse r, Response response) {
                    listener.onSuccessAddCard(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            msg = msg.replace("[]", "null");
                            msg = msg.replace("{}", "null");
                            Gson gson = new Gson();
                            CardAddResponse response = gson.fromJson(msg, CardAddResponse.class);
                            listener.onSuccessAddCard(response);
                        } catch (Exception e) {
                            try {
                                JSONObject jsonObject = new JSONObject(msg);
                                String m = jsonObject.getString("message");
                                listener.onError(m);

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, userId, addCardRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCardList(String token, String userId, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCardList(new ResponseResolver<CardListResponse>() {
                @Override
                public void onSuccess(CardListResponse r, Response response) {
                    listener.onSuccessCardList(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            CardListResponse response = gson.fromJson(msg, CardListResponse.class);
                            listener.onSuccessCardList(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
