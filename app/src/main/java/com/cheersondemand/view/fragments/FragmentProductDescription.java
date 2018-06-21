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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.productdescription.Offers;
import com.cheersondemand.model.productdescription.SimilarProductsResponse;
import com.cheersondemand.presenter.productDescription.IProductDescViewPresenter;
import com.cheersondemand.presenter.productDescription.ProductDescViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.adapter.productdescription.AdapterOffers;
import com.cheersondemand.view.adapter.productdescription.AdapterSimilarProducts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProductDescription extends Fragment implements IProductDescViewPresenter.IProductDescView {

    AllProduct product;
    @BindView(R.id.imgProduct)
    ImageView imgProduct;
    @BindView(R.id.lvOffers)
    ListView lvOffers;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvQuantity)
    TextView tvQuantity;
    @BindView(R.id.tvalcohal_vol)
    TextView tvalcohalVol;
    @BindView(R.id.tvRegion)
    TextView tvRegion;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvSeeMore)
    TextView tvSeeMore;
    @BindView(R.id.rvSimilarDrinks)
    RecyclerView rvSimilarDrinks;

    Unbinder unbinder;
    ImageLoader imageLoader;
    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.tvProductPrice)
    TextView tvProductPrice;

    AdapterOffers adapterOffers;
    LinearLayoutManager horizontalLayout;
    IProductDescViewPresenter iProductDescViewPresenter;
    AdapterSimilarProducts adapterSimilarProducts;
    @BindView(R.id.btnAddToCart)
    Button btnAddToCart;
    @BindView(R.id.btnBuyNow)
    Button btnBuyNow;

    public FragmentProductDescription() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = new ImageLoader(getActivity());
        product = (AllProduct) getArguments().getSerializable(C.PRODUCT);
        iProductDescViewPresenter = new ProductDescViewPresenterImpl(this, getActivity());
        getSimilarProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvSimilarDrinks.setLayoutManager(horizontalLayout);
        setDetail();

    }

    void getSimilarProducts() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductDescViewPresenter.getSimilarProducts("" + product.getId(), Util.id(getActivity()), "1", "10");
        } else {
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductDescViewPresenter.getSimilarProducts(token, "" + product.getId(), Util.id(getActivity()), "1", "10");

        }
    }

    void setDetail() {
        //  imageLoader.DisplayImage(product.getImage(), imgProduct);
        Util.setImage(getActivity(), product.getImage(), imgProduct);

        tvProductName.setText(product.getName());
        tvProductPrice.setText("$" + product.getPrice());
        tvType.setText(product.getSubCategory().getName());
        tvalcohalVol.setText("-");
        tvRegion.setText(product.getRegion());
        tvQuantity.setText("0.0");
        tvDesc.setText(product.getDescription());
        adapterOffers = new AdapterOffers(getActivity(), offersList());
        lvOffers.setAdapter(adapterOffers);
        Util.setListViewHeightBasedOnChildren(lvOffers);
    }

    List<Offers> offersList() {
        List<Offers> offersList = new ArrayList<>();
        offersList.add(new Offers(1, "Flat 60", "Flat 60 off on your purchase"));
        offersList.add(new Offers(2, "FLAT 20", "Flat 20 for each order"));
        return offersList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void getResponseSuccess(SimilarProductsResponse response) {
        if (response.getSuccess()) {
            if (response.getData().getSimilarProducts().size() > 0) {
                adapterSimilarProducts = new AdapterSimilarProducts(response.getData().getSimilarProducts(), getActivity());
                rvSimilarDrinks.setAdapter(adapterSimilarProducts);
            }
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
}
