package com.cheersondemand.model.search;

import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.Errors;
import com.cheersondemand.model.productdescription.Meta;
import com.cheersondemand.model.productdescription.SimilarProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AB on 7/8/2018.
 */

public class SearchProduct implements Serializable{

    @SerializedName("product")
    @Expose
    private AllProduct product;
    @SerializedName("similar_products")
    @Expose
    private List<SimilarProduct> similarProducts = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("errors")
    @Expose
    private List<Errors> errors = null;

    public AllProduct getProduct() {
        return product;
    }

    public void setProduct(AllProduct product) {
        this.product = product;
    }

    public List<SimilarProduct> getSimilarProducts() {
        return similarProducts;
    }

    public void setSimilarProducts(List<SimilarProduct> similarProducts) {
        this.similarProducts = similarProducts;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Errors> getErrors() {
        return errors;
    }

    public void setErrors(List<Errors> errors) {
        this.errors = errors;
    }

}
