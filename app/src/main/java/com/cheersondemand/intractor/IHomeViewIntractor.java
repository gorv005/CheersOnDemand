package com.cheersondemand.intractor;

import android.content.Context;

import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.CategoryRequest;

/**
 * Created by GAURAV on 7/31/2017.
 */

public interface IHomeViewIntractor {
    interface OnLoginFinishedListener {
        void onSuccess(CategoriesResponse categoriesResponse);
        void onError(String response);
        Context getAPPContext();
    }
    public void getCategories(CategoryRequest uuid, OnLoginFinishedListener listener);



}
