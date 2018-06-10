package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheersondemand.R;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.CategoryRequest;
import com.cheersondemand.model.SectionDataModel;
import com.cheersondemand.model.SingleItemModel;
import com.cheersondemand.presenter.HomeViewPresenterImpl;
import com.cheersondemand.presenter.IHomeViewPresenterPresenter;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.adapter.AdapterHomeBrands;
import com.cheersondemand.view.adapter.AdapterHomeProductsSections;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment implements IHomeViewPresenterPresenter.IHomeView{


    @BindView(R.id.rvBrands)
    RecyclerView rvBrands;
    Unbinder unbinder;
    LinearLayoutManager horizontalLayout,horizontalLayout1;
    AdapterHomeBrands adapterHomeBrands;
    AdapterHomeProductsSections adapterHomeProductsSections;
    @BindView(R.id.shimmerBrands)
    ShimmerFrameLayout shimmerBrands;
    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    @BindView(R.id.shimmerProducts)
    ShimmerFrameLayout shimmerProducts;
   ArrayList<SectionDataModel> allSampleData;
    IHomeViewPresenterPresenter iHomeViewPresenterPresenter;
    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allSampleData = new ArrayList<SectionDataModel>();
        iHomeViewPresenterPresenter=new HomeViewPresenterImpl(this,getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        horizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvBrands.setLayoutManager(horizontalLayout);


        horizontalLayout1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rvProducts.setLayoutManager(horizontalLayout1);
        rvProducts.setHasFixedSize(true);
        CategoryRequest categoryRequest=new CategoryRequest();
        categoryRequest.setUuid(Util.id(getActivity()));
        iHomeViewPresenterPresenter.getCategories(categoryRequest);
        // shimmerBrands.startShimmerAnimation();

        adapterHomeBrands = new AdapterHomeBrands(setHomeBrands(), getActivity());
        rvBrands.setAdapter(adapterHomeBrands);

    //    shimmerBrands.stopShimmerAnimation();
        createDummyData();
        adapterHomeProductsSections=new AdapterHomeProductsSections(getActivity(),allSampleData);
        rvProducts.setAdapter(adapterHomeProductsSections);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void createDummyData() {
        for (int i = 1; i <= 2; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle(getString(R.string.latest_addition));

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 10; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

    List<String> setHomeBrands() {
        List<String> brandsList = new ArrayList<>();

        brandsList.add("Beer");
        brandsList.add("Whisky");
        brandsList.add("Vodka");
        brandsList.add("Rum");
        brandsList.add("Desi daaru");

        return brandsList;
    }

    @Override
    public void getResponseSuccess(CategoriesResponse response) {
         Log.e("DEBUG",""+response.getMessage());

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