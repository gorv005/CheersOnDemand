package com.cheersondemand.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.search.SearchProductResponse;
import com.cheersondemand.model.search.SearchResponse;
import com.cheersondemand.model.search.SearchResultsResponse;
import com.cheersondemand.presenter.search.ISearchViewPresenter;
import com.cheersondemand.presenter.search.SearchViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.adapter.AdapterHomeBrands;
import com.cheersondemand.view.search.AdapterProductSearcheResults;
import com.cheersondemand.view.search.AdapterRecentProductSearches;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitySearchProducts extends Activity implements View.OnClickListener,ISearchViewPresenter.ISearchView {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.lvSearchResult)
    RecyclerView lvSearchResult;
    @BindView(R.id.llSearchResult)
    LinearLayout llSearchResult;

    @BindView(R.id.rlRecentSearch)
    LinearLayout rlRecentSearch;

    @BindView(R.id.rlCategories)
    LinearLayout rlCategories;
    @BindView(R.id.LLView)
    LinearLayout LLView;
    ISearchViewPresenter iSearchViewPresenter;
    AdapterHomeBrands adapterHomeBrands;
    @BindView(R.id.lvRecentSearches)
    RecyclerView lvRecentSearches;
    @BindView(R.id.lvCategory)
    RecyclerView lvCategory;
    LinearLayoutManager horizontalLayout, horizontalLayout1, horizontalLayout2;
    AdapterProductSearcheResults adapterProductSearcheResults;
    AdapterRecentProductSearches adapterRecentProductSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_search_products);
        ButterKnife.bind(this);
        imgBack.setOnClickListener(this);
        horizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        lvCategory.setLayoutManager(horizontalLayout);
        lvCategory.setHasFixedSize(true);
        lvCategory.setNestedScrollingEnabled(false);


        horizontalLayout1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        lvRecentSearches.setLayoutManager(horizontalLayout1);
        lvRecentSearches.setHasFixedSize(true);
        lvRecentSearches.setNestedScrollingEnabled(false);

        horizontalLayout2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        lvSearchResult.setLayoutManager(horizontalLayout2);
        lvSearchResult.setHasFixedSize(true);
        lvSearchResult.setNestedScrollingEnabled(false);


        iSearchViewPresenter = new SearchViewPresenterImpl(this, this);
        llSearchResult.setVisibility(View.GONE);
        getRecentSearch();
        init();
    }


    void init() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSearchResult(etSearch.getText().toString());
            }
        });
    }


    void getSearchResult(String query) {
        if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(this).geGuestUser(C.GUEST_USER).getId();

            iSearchViewPresenter.getSearchResults(false, "", Util.id(this), query);
        } else {
            String id = "" + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iSearchViewPresenter.getSearchResults(true, token, Util.id(this), query);

        }
    }

    void getRecentSearch() {
        if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(this).geGuestUser(C.GUEST_USER).getId();

            iSearchViewPresenter.getRecentSearches(false, "", Util.id(this));
        } else {
            String id = "" + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iSearchViewPresenter.getRecentSearches(true, token, Util.id(this));

        }
    }

    @Override
    public void onRecentSearchSuccess(SearchResponse response) {
        if (response.getSuccess()) {

            if (response.getData() != null && response.getData().getRecentSearch() != null && response.getData().getRecentSearch().size() > 0) {
                adapterRecentProductSearch = new AdapterRecentProductSearches(response.getData().getRecentSearch(), this);
                lvRecentSearches.setAdapter(adapterRecentProductSearch);
            }
            if (response.getData() != null && response.getData().getCategories() != null && response.getData().getCategories().size() > 0) {

                List<Categories> categories = new ArrayList<>();
                if (response.getData().getCategories().size() > 5) {
                    for (int i = 0; i < 5; i++) {
                        categories.add(response.getData().getCategories().get(i));
                    }
                } else {
                    categories.addAll(response.getData().getCategories());
                }
                adapterHomeBrands = new AdapterHomeBrands(categories, this);
                lvCategory.setAdapter(adapterHomeBrands);

            }

        }
    }


    public void getProductDesc(String query,String class_name,String class_id){
        if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(this).geGuestUser(C.GUEST_USER).getId();

            iSearchViewPresenter.getFetchRecordOfSearch(false, "", Util.id(this),query,class_name,class_id,"");
        } else {
            String id = "" + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iSearchViewPresenter.getFetchRecordOfSearch(true, token, Util.id(this),query,class_name,class_id,"");

        }
    }
    @Override
    public void onSearchResultSuccess(SearchResultsResponse response) {
        if (response.getSuccess()) {
            if (response.getData() != null && response.getData().getResultsCount() > 0) {
                adapterProductSearcheResults = new AdapterProductSearcheResults(response.getData().getProducts(), this);
                lvSearchResult.setAdapter(adapterProductSearcheResults);
                llSearchResult.setVisibility(View.VISIBLE);
            } else {
                llSearchResult.setVisibility(View.GONE);

            }
        } else {
            llSearchResult.setVisibility(View.GONE);

        }
    }

    @Override
    public void onSearchProductSuccess(SearchProductResponse response) {
            if(response.getSuccess()){
                Intent intent = new Intent(this, ActivityContainer.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(C.PRODUCT,response.getData().getProduct());
                intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_PRODUCT_DESC);
                intent.putExtra(C.BUNDLE,bundle);
                startActivity(intent);
            }
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
        }
    }
}
