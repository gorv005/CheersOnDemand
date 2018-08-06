package com.cheersondemand.intractor.search;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.search.SearchProductResponse;
import com.cheersondemand.model.search.SearchResponse;
import com.cheersondemand.model.search.SearchResultsResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class SearchViewIntractorImpl implements ISearchViewIntractor {

    @Override
    public void getRecentSearches( boolean isAuth,String token, String uuid,final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getRecentSearches(new ResponseResolver<SearchResponse>() {
                @Override
                public void onSuccess(SearchResponse r, Response response) {
                    listener.onRecentSearchSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                        Gson gson=new Gson();
                        SearchResponse response= gson.fromJson(msg,SearchResponse.class);
                        listener.onRecentSearchSuccess(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuth,token,uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getSearchResults(boolean isAuth, String token, String uuid, String query, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getSearchResults(new ResponseResolver<SearchResultsResponse>() {
                @Override
                public void onSuccess(SearchResultsResponse r, Response response) {
                    listener.onSearchResultSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                            Gson gson = new Gson();
                            SearchResultsResponse response = gson.fromJson(msg, SearchResultsResponse.class);
                            listener.onSearchResultSuccess(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            SearchResultsResponse searchResultsResponse=new SearchResultsResponse();
                            searchResultsResponse.setSuccess(false);
                            listener.onSearchResultSuccess(searchResultsResponse);

                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuth,token,uuid,query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getFetchRecordOfSearch(boolean isAuthUser, String token, String uuid,
                                       String query, String class_name, String class_id, String results_count, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getFetchRecordOfSearch(new ResponseResolver<SearchProductResponse>() {
                @Override
                public void onSuccess(SearchProductResponse r, Response response) {
                    listener.onSearchProductSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                            Gson gson = new Gson();
                            SearchProductResponse response = gson.fromJson(msg, SearchProductResponse.class);
                            listener.onSearchProductSuccess(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            SearchProductResponse searchResultsResponse=new SearchProductResponse();
                            searchResultsResponse.setSuccess(false);
                            listener.onSearchProductSuccess(searchResultsResponse);

                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,uuid,query,class_name,class_id,results_count);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
