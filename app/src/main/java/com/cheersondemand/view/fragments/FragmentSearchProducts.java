package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.AdapterSearchCategories;
import com.cheersondemand.view.search.AdapterProductSearcheResults;
import com.cheersondemand.view.search.AdapterRecentProductSearches;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearchProducts extends Fragment implements ISearchViewPresenter.ISearchView {


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
    AdapterSearchCategories adapterHomeBrands;
    @BindView(R.id.lvRecentSearches)
    RecyclerView lvRecentSearches;

    LinearLayoutManager horizontalLayout, horizontalLayout1, horizontalLayout2;
    AdapterProductSearcheResults adapterProductSearcheResults;
    AdapterRecentProductSearches adapterRecentProductSearch;
    Util util;
    @BindView(R.id.tvRecenetSearch)
    TextView tvRecenetSearch;
    boolean isRecentSearch = false;
    List<Product> searchProducts = null;
    @BindView(R.id.lvCategory)
    RecyclerView lvCategory;
    Unbinder unbinder;
    int source;
    public FragmentSearchProducts() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        source = getArguments().getInt(C.SOURCE);

        iSearchViewPresenter = new SearchViewPresenterImpl(this, getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_products, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (source == C.FRAGMENT_SEARCH_PRODUCT) {
            ((ActivityContainer)getActivity()).setTitle("");
            ((ActivityContainer) getActivity()).hideToolBar();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        lvCategory.setLayoutManager(horizontalLayout);
        lvCategory.setHasFixedSize(true);
        lvCategory.setNestedScrollingEnabled(false);


        horizontalLayout1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        lvRecentSearches.setLayoutManager(horizontalLayout1);
        lvRecentSearches.setHasFixedSize(true);
        lvRecentSearches.setNestedScrollingEnabled(false);

        horizontalLayout2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        lvSearchResult.setLayoutManager(horizontalLayout2);
        lvSearchResult.setHasFixedSize(true);
        lvSearchResult.setNestedScrollingEnabled(false);


        llSearchResult.setVisibility(View.GONE);
        getRecentSearch();
        init();
    }
    void init() {
        try {
            etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if (searchProducts != null && searchProducts.size() == 0 && etSearch.getText().toString().length() > 2) {
                            Intent intent = new Intent(getActivity(), ActivityContainer.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(C.PRODUCT, etSearch.getText().toString());
                            intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_SEARCH_PRODUCT_RESULTS);
                            intent.putExtra(C.BUNDLE, bundle);
                            startActivity(intent);
                        }
                        return true;
                    }
                    return false;
                }
            });
            etSearch.setKeyImeChangeListener(new ChatEditText.KeyImeChange() {
                @Override
                public void onKeyIme(int keyCode, KeyEvent event) {
                    if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
                        etSearch.clearFocus();
                        rlRecentSearch.setVisibility(View.VISIBLE);
                        if (isRecentSearch) {
                            tvRecenetSearch.setVisibility(View.VISIBLE);
                        } else {
                            tvRecenetSearch.setVisibility(View.GONE);

                        }
                        rlCategories.setVisibility(View.GONE);
                    }
                }
            });
            etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {

                        if (hasFocus) {
                            rlRecentSearch.setVisibility(View.GONE);
                            rlCategories.setVisibility(View.GONE);
                            tvRecenetSearch.setVisibility(View.GONE);
                        } else {
                            rlRecentSearch.setVisibility(View.VISIBLE);
                            if (isRecentSearch) {
                                tvRecenetSearch.setVisibility(View.VISIBLE);
                            } else {
                                tvRecenetSearch.setVisibility(View.GONE);

                            }
                            rlCategories.setVisibility(View.GONE);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
        catch (Exception e){
            e.printStackTrace();
        }

    }
    void getSearchResult(String query) {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iSearchViewPresenter.getSearchResults(false, "", Util.id(getActivity()), query);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iSearchViewPresenter.getSearchResults(true, token, Util.id(getActivity()), query);

        }
    }

    void getRecentSearch() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iSearchViewPresenter.getRecentSearches(false, "", Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iSearchViewPresenter.getRecentSearches(true, token, Util.id(getActivity()));

        }
    }

    @Override
    public void onRecentSearchSuccess(SearchResponse response) {
        try {
            if (response.getSuccess()) {

                if (response.getData() != null && response.getData().getRecentSearch() != null && response.getData().getRecentSearch().size() > 0) {
                    rlRecentSearch.setVisibility(View.VISIBLE);
                    tvRecenetSearch.setVisibility(View.VISIBLE);
                    isRecentSearch = true;
                    adapterRecentProductSearch = new AdapterRecentProductSearches(source, response.getData().getRecentSearch(), getActivity());
                    lvRecentSearches.setAdapter(adapterRecentProductSearch);
                } else {
                    isRecentSearch = false;
                    rlRecentSearch.setVisibility(View.GONE);
                    tvRecenetSearch.setVisibility(View.GONE);
                }
                if (response.getData() != null && response.getData().getCategories() != null && response.getData().getCategories().size() > 0) {
                    boolean isViewMore = false;
                    List<Categories> categories = new ArrayList<>();
                    if (response.getData().getCategories().size() > 5) {
                        isViewMore = true;
                        for (int i = 0; i < 5; i++) {
                            categories.add(response.getData().getCategories().get(i));
                        }
                    } else {
                        categories.addAll(response.getData().getCategories());
                    }
                    //  adapterHomeBrands = new AdapterSearchCategories(source,isViewMore,categories, getActivity());
                    // lvCategory.setAdapter(adapterHomeBrands);


                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getProductDesc(String query, String class_name, String class_id) {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iSearchViewPresenter.getFetchRecordOfSearch(false, "", Util.id(getActivity()), query, class_name, class_id, "5");
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iSearchViewPresenter.getFetchRecordOfSearch(true, token, Util.id(getActivity()), query, class_name, class_id, "5");

        }
    }

    @Override
    public void onSearchResultSuccess(SearchResultsResponse response) {
        if (response.getSuccess()) {

            if (response.getData() != null && response.getData().getResultsCount() > 0) {
                llSearchResult.setVisibility(View.VISIBLE);
                searchProducts=response.getData().getProducts();
                /*if(adapterProductSearcheResults!=null){
                    adapterProductSearcheResults.notifyDataSetChanged();
                }
                else {*/
                adapterProductSearcheResults = new AdapterProductSearcheResults(source,searchProducts, getActivity());
                lvSearchResult.setAdapter(adapterProductSearcheResults);
                //}
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
            Intent intent = new Intent(getActivity(), ActivityContainer.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(C.PRODUCT, response.getData().getProduct());
            intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_DESC);
            intent.putExtra(C.BUNDLE, bundle);
            startActivity(intent);
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, LLView, true);

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
