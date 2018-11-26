package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SubCategory;
import com.cheersondemand.model.SubCategoryResponse;
import com.cheersondemand.model.deals.DealsResponse;
import com.cheersondemand.presenter.home.HomeViewPresenterImpl;
import com.cheersondemand.presenter.home.IHomeViewPresenterPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityFilters;
import com.cheersondemand.view.adapter.filter.AdapterSubCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSubcategoryFilter extends Fragment implements IHomeViewPresenterPresenter.IHomeView {


    @BindView(R.id.etSubCatName)
    EditText etSubCatName;
    @BindView(R.id.lvSubCategories)
    ListView lvSubCategories;
    @BindView(R.id.rlSubCategories)
    LinearLayout rlSubCategories;
    @BindView(R.id.LLView)
    RelativeLayout LLView;
    Unbinder unbinder;
    List<Categories> categoriesList;

    List<Integer> catIdList;
    IHomeViewPresenterPresenter iHomeViewPresenterPresenter;
    List<SubCategory> subCategoryList;

    List<SubCategory> subCategoryListFilter;

    AdapterSubCategory adapterSubCategory;
    Util util;
    public FragmentSubcategoryFilter() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subcategory_filter, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catIdList=new ArrayList<>();
        util=new Util();
        iHomeViewPresenterPresenter=new HomeViewPresenterImpl(this,getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesList=  ((ActivityFilters)getActivity()).getCategoryListFilter();
        subCategoryListFilter=  ((ActivityFilters)getActivity()).getSubCatList();
        if(categoriesList!=null) {
            for (int i = 0; i < categoriesList.size(); i++) {
                if (categoriesList.get(i).isSelected()) {
                    catIdList.add(categoriesList.get(i).getId());
                }
            }
        }
        getSubCatList();
    }

    public List<SubCategory> getSubCategoriesList(){
        try {
            if(adapterSubCategory!=null) {
                return adapterSubCategory.getSubCategoryList();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    void getSubCatList(){
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iHomeViewPresenterPresenter.getSubCategories(false, "", catIdList,Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getSubCategories(true, token, catIdList,Util.id(getActivity()));
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
        try {

            if (response.getSuccess()) {
                subCategoryList = response.getData();
                for (int i = 0; i < subCategoryList.size(); i++) {
                    subCategoryList.get(i).setPos(i);
                }
                if (subCategoryListFilter != null) {
                    for (int i = 0; i < subCategoryList.size(); i++) {
                        for (int j = 0; j < subCategoryListFilter.size(); j++) {
                            if (subCategoryList.get(i).getId().equals(subCategoryListFilter.get(i).getId()) && subCategoryListFilter.get(i).isSelected()) {
                                subCategoryList.get(i).setSelected(true);
                                break;
                            }
                        }
                    }
                }
                if (subCategoryList != null && subCategoryList.size() > 0) {
                    adapterSubCategory = new AdapterSubCategory(getActivity(), subCategoryList);
                    lvSubCategories.setAdapter(adapterSubCategory);

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getDealsResponse(DealsResponse response) {

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
