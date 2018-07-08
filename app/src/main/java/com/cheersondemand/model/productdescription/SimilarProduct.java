package com.cheersondemand.model.productdescription;

import com.cheersondemand.model.AllProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AB on 6/22/2018.
 */

public class SimilarProduct implements Serializable{
    @SerializedName("product")
    @Expose
    private AllProduct product;
    @SerializedName("similar_products")
    @Expose
    private List<AllProduct> similarProducts = null;

    public AllProduct getProduct() {
        return product;
    }

    public void setProduct(AllProduct product) {
        this.product = product;
    }

    public List<AllProduct> getSimilarProducts() {
        return similarProducts;
    }

    public void setSimilarProducts(List<AllProduct> similarProducts) {
        this.similarProducts = similarProducts;
    }
}
