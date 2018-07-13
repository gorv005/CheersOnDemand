package com.cheersondemand.model.card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 7/14/2018.
 */

public class AddCardRequest {
    @SerializedName("stripe_token")
    @Expose
    private String stripeToken;

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

}
