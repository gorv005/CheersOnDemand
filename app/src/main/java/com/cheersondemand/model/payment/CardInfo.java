package com.cheersondemand.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 7/18/2018.
 */

public class CardInfo {
    @SerializedName("stripe_token")
    @Expose
    private String stripeToken;
    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("cardNumber")
    @Expose
    private Boolean cardNumber;
    @SerializedName("cardHolder")
    @Expose
    private String cardHolder;
    @SerializedName("cvv")
    @Expose
    private String cvv;
    @SerializedName("expireMonth")
    @Expose
    private String expireMonth;
    @SerializedName("expireYear")
    @Expose
    private String expireYear;
    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Boolean getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Boolean cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }
}
