package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SubCategoryResponse;
import com.cheersondemand.presenter.home.HomeViewPresenterImpl;
import com.cheersondemand.presenter.home.IHomeViewPresenterPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.AdapterCategories;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategoryList extends Fragment implements IHomeViewPresenterPresenter.IHomeView,View.OnClickListener {


    @BindView(R.id.rvBrands)
    RecyclerView rvBrands;
    Unbinder unbinder;
    @BindView(R.id.rlView)
    LinearLayout rlView;
    LinearLayoutManager layoutManager;
    AdapterCategories adapterCategories;
    Util util;
    IHomeViewPresenterPresenter iHomeViewPresenterPresenter;
    @BindView(R.id.imgBack)
    RelativeLayout imgBack;
    @BindView(R.id.tvExplore)
    TextView tvExplore;
    @BindView(R.id.rlBar)
    RelativeLayout rlBar;
    @BindView(R.id.viewLine)
    View viewLine;
    int source;
    public FragmentCategoryList() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        /*((ActivityContainer)getActivity()).showToolBar();
        ActivityContainer.tvTitle.setText(R.string.Explore);*/
        if(source!=C.FRAGMENT_PRODUCTS_HOME) {
            ((ActivityContainer) getActivity()).hideToolBar();
        }
        getCategories();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iHomeViewPresenterPresenter = new HomeViewPresenterImpl(this, getActivity());
        source = getArguments().getInt(C.SOURCE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvBrands.setLayoutManager(layoutManager);
        rvBrands.setHasFixedSize(true);
        imgBack.setOnClickListener(this);
    }


    void getCategories() {
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iHomeViewPresenterPresenter.getCategories(false, "", Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getCategories(true, token, Util.id(getActivity()));
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
    public void getResponseSuccess(CategoriesResponse response) {
        try {
        if (response.getSuccess()) {
            adapterCategories = new AdapterCategories(response.getData(), getActivity());
            rvBrands.setAdapter(adapterCategories);
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlView, true);

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                getActivity().onBackPressed();
                break;
        }
    }
}
