package com.cheersondemand.presenter.search;

import com.cheersondemand.model.search.SearchProductResponse;
import com.cheersondemand.model.search.SearchResponse;
import com.cheersondemand.model.search.SearchResultsResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface ISearchViewPresenter {

    public void getRecentSearches(boolean isAuth,String token, String uuid);
    public void getSearchResults(boolean isAuth,String token, String uuid, String query);
    public void getFetchRecordOfSearch(boolean isAuthUser, String token, String uuid, String query,String class_name,String class_id,String results_count);


    void onDestroy();

    interface ISearchView {
        public void onRecentSearchSuccess(SearchResponse response);
        void onSearchResultSuccess(SearchResultsResponse response);
        void onSearchProductSuccess(SearchProductResponse response);

        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
