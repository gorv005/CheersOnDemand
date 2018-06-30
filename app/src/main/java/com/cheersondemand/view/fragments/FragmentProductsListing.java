package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.productList.ProductListResponse;
import com.cheersondemand.presenter.products.IProductViewPresenter;
import com.cheersondemand.presenter.products.ProductViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.products.AdapterProducts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProductsListing extends Fragment implements IProductViewPresenter.IProductView {


    @BindView(R.id.llSort)
    LinearLayout llSort;
    @BindView(R.id.llFilter)
    LinearLayout llFilter;
    @BindView(R.id.rvProductsList)
    RecyclerView rvProductsList;
    Unbinder unbinder;
    Util util;
    IProductViewPresenter iProductViewPresenter;
    AdapterProducts adapterProducts;
    List<AllProduct> allProductList;
    int page = 1;
    int perPage = 10;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    private GridLayoutManager lLayout;
    String catId;
    long from=0,to=5000;
     String orderBy="desc";
     String orderField="created_at";
    public FragmentProductsListing() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iProductViewPresenter = new ProductViewPresenterImpl(this, getActivity());
        catId=getArguments().getString(C.CAT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories_products_listing, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lLayout = new GridLayoutManager(getActivity(), 2);
        rvProductsList.setHasFixedSize(true);
        rvProductsList.setLayoutManager(lLayout);
        getProductsList(page, perPage,catId,""+from,""+to,orderBy,orderField);
    }
    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(R.string.product_listing);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    void getProducts(int page, int perPage) {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductViewPresenter.getProductList(false, "", Util.id(getActivity()), "" + page, "" + perPage);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductViewPresenter.getProductList(true, token, Util.id(getActivity()), "" + page, "" + perPage);
        }
    }
    void getProductsList(int page, int perPage,String catId,String from ,String to,String orderBy,String orderField) {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductViewPresenter.getProductList(false, "", Util.id(getActivity()), "" + page, "" + perPage,catId,from,to,orderBy,orderField);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductViewPresenter.getProductList(true, token, Util.id(getActivity()), "" + page, "" + perPage,catId,from,to,orderBy,orderField);
        }
    }
    @Override
    public void getProductListSuccess(ProductListResponse response) {
        if (response.getSuccess()) {
            allProductList = response.getData();
            adapterProducts = new AdapterProducts(allProductList, getActivity());
            rvProductsList.setAdapter(adapterProducts);
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlView, true);

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
