package com.cheersondemand.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 7/18/2018.
 */

public class PaymentRequest {
    @SerializedName("stripe_token")
    @Expose
    private String stripeToken;
    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("is_gift")
    @Expose
    private Boolean isGift;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("schedule_time")
    @Expose
    private String scheduleTime;
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

    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
