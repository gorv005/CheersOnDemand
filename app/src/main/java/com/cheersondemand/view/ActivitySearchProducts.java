package com.cheersondemand.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.search.Product;
import com.cheersondemand.model.search.SearchProductResponse;
import com.cheersondemand.model.search.SearchResponse;
import com.cheersondemand.model.search.SearchResultsResponse;
import com.cheersondemand.presenter.search.ISearchViewPresenter;
import com.cheersondemand.presenter.search.SearchViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ChatEditText;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.adapter.AdapterHomeBrands;
import com.cheersondemand.view.search.AdapterProductSearcheResults;
import com.cheersondemand.view.search.AdapterRecentProductSearches;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitySearchProducts extends Activity implements View.OnClickListener, ISearchViewPresenter.ISearchView {

    @BindView(R.id.imgBack)
    RelativeLayout imgBack;
    @BindView(R.id.etSearch)
    ChatEditText etSearch;
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
    Util util;
    @BindView(R.id.tvRecenetSearch)
    TextView tvRecenetSearch;
    boolean isRecentSearch=false;
    List<Product> searchProducts=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_search_products);
        ButterKnife.bind(this);
        imgBack.setOnClickListener(this);
        util = new Util();
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

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchProducts!=null && searchProducts.size()==0 && etSearch.getText().toString().length() > 2) {
                        Intent intent = new Intent(ActivitySearchProducts.this, ActivityContainer.class);
                        Bundle bundle=new Bundle();
                        bundle.putString(C.PRODUCT,etSearch.getText().toString());
                        intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_SEARCH_PRODUCT_RESULTS);
                        intent.putExtra(C.BUNDLE,bundle);
                       startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
        etSearch.setKeyImeChangeListener(new ChatEditText.KeyImeChange(){
            @Override
            public void onKeyIme(int keyCode, KeyEvent event)
            {
                if (KeyEvent.KEYCODE_BACK == event.getKeyCode())
                {
                    etSearch.clearFocus();
                    rlRecentSearch.setVisibility(View.VISIBLE);
                    if(isRecentSearch) {
                        tvRecenetSearch.setVisibility(View.VISIBLE);
                    }
                    else {
                        tvRecenetSearch.setVisibility(View.GONE);

                    }
                    rlCategories.setVisibility(View.VISIBLE);
                }
            }});
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    rlRecentSearch.setVisibility(View.GONE);
                    rlCategories.setVisibility(View.GONE);
                    tvRecenetSearch.setVisibility(View.GONE);
                }
                else {
                    rlRecentSearch.setVisibility(View.VISIBLE);
                    if(isRecentSearch) {
                        tvRecenetSearch.setVisibility(View.VISIBLE);
                    }
                    else {
                        tvRecenetSearch.setVisibility(View.GONE);

                    }
                    rlCategories.setVisibility(View.VISIBLE);

                }
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etSearch.getText().toString().length() > 2) {
                    getSearchResult(etSearch.getText().toString());
                } else {
                    llSearchResult.setVisibility(View.GONE);
                }
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
                rlRecentSearch.setVisibility(View.VISIBLE);
                tvRecenetSearch.setVisibility(View.GONE);
                isRecentSearch=true;
                adapterRecentProductSearch = new AdapterRecentProductSearches(response.getData().getRecentSearch(), this);
                lvRecentSearches.setAdapter(adapterRecentProductSearch);
            } else {
                isRecentSearch=false;
                rlRecentSearch.setVisibility(View.GONE);
                tvRecenetSearch.setVisibility(View.GONE);
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


    public void getProductDesc(String query, String class_name, String class_id) {
        if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(this).geGuestUser(C.GUEST_USER).getId();

            iSearchViewPresenter.getFetchRecordOfSearch(false, "", Util.id(this), query, class_name, class_id, "5");
        } else {
            String id = "" + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iSearchViewPresenter.getFetchRecordOfSearch(true, token, Util.id(this), query, class_name, class_id, "5");

        }
    }

    @Override
    public void onSearchResultSuccess(SearchResultsResponse response) {
        if (response.getSuccess()) {

            if (response.getData() != null && response.getData().getResultsCount() > 0) {
                llSearchResult.setVisibility(View.VISIBLE);
                searchProducts=response.getData().getProducts();
                if(adapterProductSearcheResults!=null){
                    adapterProductSearcheResults.notifyDataSetChanged();
                }
                else {
                    adapterProductSearcheResults = new AdapterProductSearcheResults(searchProducts, this);
                    lvSearchResult.setAdapter(adapterProductSearcheResults);
                }
            } else {
                if(searchProducts!=null && searchProducts.size()>0) {
                    searchProducts.clear();
                }
                llSearchResult.setVisibility(View.GONE);

            }
        } else {
            if(searchProducts!=null && searchProducts.size()>0) {
                searchProducts.clear();

            }
            llSearchResult.setVisibility(View.GONE);

        }
    }

    @Override
    public void onSearchProductSuccess(SearchProductResponse response) {
        if (response.getSuccess()) {
            Intent intent = new Intent(this, ActivityContainer.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(C.PRODUCT, response.getData().getProduct());
            intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_DESC);
            intent.putExtra(C.BUNDLE, bundle);
            startActivity(intent);
        } else {
            util.setSnackbarMessage(this, response.getMessage(), LLView, true);

        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(this, response, LLView, true);

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, this);

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
        }
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*try {
            etSearch.clearFocus();
            rlRecentSearch.setVisibility(View.VISIBLE);
            tvRecenetSearch.setVisibility(View.VISIBLE);
            rlCategories.setVisibility(View.VISIBLE);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
