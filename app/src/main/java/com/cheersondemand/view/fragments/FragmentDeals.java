package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SubCategoryResponse;
import com.cheersondemand.model.deals.Deals;
import com.cheersondemand.model.deals.DealsResponse;
import com.cheersondemand.presenter.home.HomeViewPresenterImpl;
import com.cheersondemand.presenter.home.IHomeViewPresenterPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.util.itemdecoration.GridSpacingItemDecoration;
import com.cheersondemand.view.adapter.explore.AdapterDeals;
import com.cheersondemand.view.adapter.explore.AdapterSubCategories;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDeals extends Fragment implements IHomeViewPresenterPresenter.IHomeView{


    @BindView(R.id.rvDeals)
    RecyclerView rvDeals;
    @BindView(R.id.llView)
    LinearLayout llView;
    Unbinder unbinder;
    Util util;
    IHomeViewPresenterPresenter iHomeViewPresenterPresenter;
    AdapterDeals adapterDeals;
    public FragmentDeals() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util=new Util();
        iHomeViewPresenterPresenter = new HomeViewPresenterImpl(this, getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deals, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDeals.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvDeals.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(1, getActivity()), true));
        rvDeals.setItemAnimator(new DefaultItemAnimator());
        getDeals();
    }
    void getDeals() {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iHomeViewPresenterPresenter.getDeals(false, "", Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getDeals(true, token, Util.id(getActivity()));
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getProductWithCategoriesSuccess(ProductsWithCategoryResponse response) {

    }

    @Override
    public void getBrandResponseSuccess(BrandResponse response) {

    }

    @Override
    public void getResponseSuccessSubCat(SubCategoryResponse response) {

    }

    @Override
    public void getDealsResponse(DealsResponse response) {
        try {

            if (response.getSuccess()) {
                Deals deals= response.getData();
                adapterDeals=new AdapterDeals(true,deals.getProducts(),getActivity());
                rvDeals.setAdapter(adapterDeals);
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, true);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseSuccess(CategoriesResponse response) {

    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }

}
