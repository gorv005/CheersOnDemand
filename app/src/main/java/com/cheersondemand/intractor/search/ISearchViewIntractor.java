package com.cheersondemand.intractor.search;

import android.content.Context;

import com.cheersondemand.model.search.SearchProductResponse;
import com.cheersondemand.model.search.SearchResponse;
import com.cheersondemand.model.search.SearchResultsResponse;

/**
 * Created by AB on 7/31/2017.
 */

public interface ISearchViewIntractor {
    interface OnFinishedListener {
        void onRecentSearchSuccess(SearchResponse Response);
        void onSearchResultSuccess(SearchResultsResponse response);
        void onSearchProductSuccess(SearchProductResponse response);

        void onError(String response);
        Context getAPPContext();
    }
    public void getRecentSearches(boolean isAuth,String token, String uuid, OnFinishedListener listener);
    public void getSearchResults(boolean isAuth,String token, String uuid, String query,OnFinishedListener listener);
    public void getFetchRecordOfSearch(boolean isAuthUser, String token, String uuid, String query,String class_name,String class_id,String results_count,OnFinishedListener listener);


}
