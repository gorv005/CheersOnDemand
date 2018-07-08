package com.cheersondemand.model;

import java.util.List;

/**
 * Created by AB on 6/15/2018.
 */

public class HomeCategoriesSectionList {

    private String headerTitle;
    private List<AllProduct> allProducts;

    public HomeCategoriesSectionList(String headerTitle, List<AllProduct> allProducts) {
        this.headerTitle = headerTitle;
        this.allProducts = allProducts;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public List<AllProduct> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<AllProduct> allProducts) {
        this.allProducts = allProducts;
    }
}
