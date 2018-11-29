package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.graphics.Rect;
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
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SubCategoryResponse;
import com.cheersondemand.model.deals.DealsResponse;
import com.cheersondemand.presenter.home.HomeViewPresenterImpl;
import com.cheersondemand.presenter.home.IHomeViewPresenterPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.adapter.explore.AdapterCategoriesSubcategories;
import com.cheersondemand.view.adapter.explore.AdapterSubCategories;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategorySubCategory extends Fragment implements IHomeViewPresenterPresenter.IHomeView {

    @BindView(R.id.llView)
    LinearLayout llView;
    Unbinder unbinder;
    Util util;
    IHomeViewPresenterPresenter iHomeViewPresenterPresenter;
    AdapterSubCategories adapterSubCategories;
    @BindView(R.id.lvExpCategory)
    ExpandableListView lvExpCategory;
    AdapterCategoriesSubcategories adapterCategoriesSubcategories;
    public FragmentCategorySubCategory() {
        // Required empty public constructor
    }
    public static FragmentCategorySubCategory newInstance() {
        FragmentCategorySubCategory fragmentFirst = new FragmentCategorySubCategory();
        return fragmentFirst;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iHomeViewPresenterPresenter = new HomeViewPresenterImpl(this, getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_sub_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvExpCategory.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
        lvExpCategory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {

                if(groupPosition != previousGroup)
                    lvExpCategory.collapseGroup(previousGroup);
                previousGroup = groupPosition;


            }
        });

        getCategories();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void getCategories() {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iHomeViewPresenterPresenter.getCategories(false, "", Util.id(getActivity()), "" + true);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getCategories(true, token, Util.id(getActivity()), "" + true);
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

    }

    @Override
    public void getResponseSuccess(CategoriesResponse response) {
        try {

            if (response.getSuccess()) {
                List<Categories> categories = response.getData();
                adapterCategoriesSubcategories=new AdapterCategoriesSubcategories(getActivity(),categories);
                lvExpCategory.setAdapter(adapterCategoriesSubcategories);
              /*  adapterSubCategories = new AdapterSubCategories(true, categories.get(0).getSubCategories(), getActivity());
                rvSubCategory.setAdapter(adapterSubCategories);*/
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
