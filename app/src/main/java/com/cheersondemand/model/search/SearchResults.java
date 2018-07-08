package com.cheersondemand.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GAURAV on 7/8/2018.
 */

public class SearchResults {
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("results_count")
    @Expose
    private Integer resultsCount;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(Integer resultsCount) {
        this.resultsCount = resultsCount;
    }

}
