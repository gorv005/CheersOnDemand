package com.cheersondemand.presenter.search;

import android.content.Context;

import com.cheersondemand.intractor.search.ISearchViewIntractor;
import com.cheersondemand.intractor.search.SearchViewIntractorImpl;
import com.cheersondemand.model.search.SearchProductResponse;
import com.cheersondemand.model.search.SearchResponse;
import com.cheersondemand.model.search.SearchResultsResponse;


public class SearchViewPresenterImpl implements ISearchViewPresenter, ISearchViewIntractor.OnFinishedListener {

    ISearchView mView;
    Context context;
    ISearchViewIntractor iSearchViewIntractor ;

    public SearchViewPresenterImpl(ISearchView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iSearchViewIntractor = new SearchViewIntractorImpl();

    }



    @Override
    public void onRecentSearchSuccess(SearchResponse Response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.onRecentSearchSuccess(Response);
        }
    }

    @Override
    public void onSearchResultSuccess(SearchResultsResponse Response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.onSearchResultSuccess(Response);
        }
    }

    @Override
    public void onSearchProductSuccess(SearchProductResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.onSearchProductSuccess(response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }



    @Override
    public void getRecentSearches(boolean isAuth, String token, String uuid) {
        if (mView != null) {

            iSearchViewIntractor.getRecentSearches(isAuth,token,uuid, this);
        }
    }

    @Override
    public void getSearchResults(boolean isAuth, String token, String uuid, String query) {
        if (mView != null) {

            iSearchViewIntractor.getSearchResults(isAuth,token,uuid,query, this);
        }
    }

    @Override
    public void getFetchRecordOfSearch(boolean isAuthUser, String token, String uuid, String query, String class_name, String class_id, String results_count) {
        if (mView != null) {

            iSearchViewIntractor.getFetchRecordOfSearch(isAuthUser,token,uuid,query,class_name,class_id, results_count, this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
