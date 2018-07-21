package com.cheersondemand.model.card;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardList {

    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("exp_month")
    @Expose
    private Integer expMonth;
    @SerializedName("exp_year")
    @Expose
    private Integer expYear;
    @SerializedName("last4")
    @Expose
    private String last4;
    @SerializedName("cardNumber")
    @Expose
    private String cardNumber;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("is_default_source")
    @Expose
    private Boolean isDefaultSource;
    @SerializedName("card_holder_name")
    @Expose
    private String cardHolderName;
    @SerializedName("stripe_token")
    @Expose
    private String stripeToken;
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(Integer expMonth) {
        this.expMonth = expMonth;
    }

    public Integer getExpYear() {
        return expYear;
    }

    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getIsDefaultSource() {
        return isDefaultSource;
    }

    public void setIsDefaultSource(Boolean isDefaultSource) {
        this.isDefaultSource = isDefaultSource;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
}
