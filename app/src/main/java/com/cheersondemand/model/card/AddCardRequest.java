package com.cheersondemand.model.card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class AddCardRequest {
    @SerializedName("stripe_token")
    @Expose
    private String stripeToken;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
