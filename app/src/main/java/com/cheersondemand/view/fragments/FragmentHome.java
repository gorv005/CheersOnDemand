package com.cheersondemand.view.fragments;


import android.content.Intent;
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
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.HomeCategoriesSectionList;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SectionDataModel;
import com.cheersondemand.model.SingleItemModel;
import com.cheersondemand.model.location.SelectedLocation;
import com.cheersondemand.model.store.StoreList;
import com.cheersondemand.presenter.home.HomeViewPresenterImpl;
import com.cheersondemand.presenter.home.IHomeViewPresenterPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivitySearchLocation;
import com.cheersondemand.view.adapter.AdapterHomeBrands;
import com.cheersondemand.view.adapter.AdapterHomeCategoriesSections;
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
public class FragmentHome extends Fragment implements IHomeViewPresenterPresenter.IHomeView, View.OnClickListener {


    @BindView(R.id.rvBrands)
    RecyclerView rvBrands;
    Unbinder unbinder;
    LinearLayoutManager horizontalLayout, horizontalLayout1;
    AdapterHomeBrands adapterHomeBrands;
    AdapterHomeProductsSections adapterHomeProductsSections;
    AdapterHomeCategoriesSections adapterHomeCategoriesSections;

    @BindView(R.id.shimmerBrands)
    ShimmerFrameLayout shimmerBrands;
    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    @BindView(R.id.shimmerProducts)
    ShimmerFrameLayout shimmerProducts;
    ArrayList<SectionDataModel> allSampleData;
    IHomeViewPresenterPresenter iHomeViewPresenterPresenter;
    @BindView(R.id.rlNotification)
    RelativeLayout rlNotification;
    StoreList store;
    List<SelectedLocation> selectedLocation;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;
    @BindView(R.id.tvLocationName)
    TextView tvLocationName;
    @BindView(R.id.tvStoreName)
    TextView tvStoreName;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.llLocationSelect)
    LinearLayout llLocationSelect;
    @BindView(R.id.llStoreSelect)
    LinearLayout llStoreSelect;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allSampleData = new ArrayList<SectionDataModel>();
        iHomeViewPresenterPresenter = new HomeViewPresenterImpl(this, getActivity());
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
    public void onResume() {
        super.onResume();
        store = SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE);
        if (store != null) {
            tvStoreName.setText(store.getName());

        }
        selectedLocation = SharedPreference.getInstance(getActivity()).getRecentLocations(C.LOCATION_SELECTED);
        if (selectedLocation != null && selectedLocation.size()>0) {
            tvLocationName.setText(selectedLocation.get(selectedLocation.size()-1).getName());
        }
       /* CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setUuid(Util.id(getActivity()));*/
        // iHomeViewPresenterPresenter.getCategories(categoryRequest);
        //  iHomeViewPresenterPresenter.getBrands(SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken(),categoryRequest);
        shimmerBrands.startShimmerAnimation();
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iHomeViewPresenterPresenter.getProductWithCategories(Util.id(getActivity()));
        }
        else {
            String token="bearer "+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getProductWithCategories(token,Util.id(getActivity()));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlNotification.setOnClickListener(this);
        llLocationSelect.setOnClickListener(this);
        llStoreSelect.setOnClickListener(this);
        horizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvBrands.setLayoutManager(horizontalLayout);


        horizontalLayout1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rvProducts.setLayoutManager(horizontalLayout1);
        rvProducts.setHasFixedSize(true);

  /*      adapterHomeBrands = new AdapterHomeBrands(setHomeBrands(), getActivity());
        rvBrands.setAdapter(adapterHomeBrands);
*/
        //    shimmerBrands.stopShimmerAnimation();


        /*createDummyData();
        adapterHomeProductsSections = new AdapterHomeProductsSections(getActivity(), allSampleData);
        rvProducts.setAdapter(adapterHomeProductsSections);*/
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
    public void getProductWithCategoriesSuccess(ProductsWithCategoryResponse response) {
        if (response.getSuccess()) {
            shimmerBrands.stopShimmerAnimation();
            adapterHomeBrands = new AdapterHomeBrands(response.getData().getCategories(), getActivity());
            rvBrands.setAdapter(adapterHomeBrands);

            ArrayList<HomeCategoriesSectionList> homeCategoriesSectionList = new ArrayList<>();
            homeCategoriesSectionList.add(new HomeCategoriesSectionList("ALL Products", response.getData().getAllProducts()));

            adapterHomeCategoriesSections = new AdapterHomeCategoriesSections(getActivity(), homeCategoriesSectionList);
            rvProducts.setAdapter(adapterHomeCategoriesSections);
        }
    }

    @Override
    public void getResponseSuccess(CategoriesResponse response) {
        // Log.e("DEBUG", "" + response.getMessage());
        if (response.getSuccess()) {
            shimmerBrands.stopShimmerAnimation();
            adapterHomeBrands = new AdapterHomeBrands(response.getData(), getActivity());
            rvBrands.setAdapter(adapterHomeBrands);
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
            case R.id.rlNotification:
                Intent intent = new Intent(getActivity(), ActivityContainer.class);
                Bundle bundle = new Bundle();
                intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_NOTIFICATIONS);
                intent.putExtra(C.BUNDLE, bundle);
                startActivity(intent);
                break;
            case R.id.llStoreSelect:
                gotoStoreList();
                break;

            case R.id.llLocationSelect:
                gotoLocation();
                break;
        }
    }


    void gotoStoreList(){
        Intent intent=new Intent(getActivity(),ActivityContainer.class);
        Bundle bundle=new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_STORE_LIST);
        bundle.putInt(C.FROM,C.HOME);

        intent.putExtra(C.BUNDLE,bundle);
        startActivity(intent);
    }
    void gotoLocation(){
        Intent intent=new Intent(getActivity(),ActivitySearchLocation.class);

        intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_STORE_LIST);
        intent.putExtra(C.FROM,C.HOME);
        startActivity(intent);
    }
}
