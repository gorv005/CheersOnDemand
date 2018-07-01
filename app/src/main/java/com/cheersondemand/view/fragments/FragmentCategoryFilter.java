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
import com.cheersondemand.model.Categories;
import com.cheersondemand.view.adapter.filter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategoryFilter extends Fragment {


    @BindView(R.id.etCatName)
    EditText etCatName;
    @BindView(R.id.lvCategories)
    ListView lvCategories;
    @BindView(R.id.rlCategories)
    LinearLayout rlCategories;
    @BindView(R.id.LLView)
    RelativeLayout LLView;
    Unbinder unbinder;
    CategoryAdapter adapterCategories;
    public FragmentCategoryFilter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_filter, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterCategories=new CategoryAdapter(getActivity(),getCat());
        lvCategories.setAdapter(adapterCategories);
    }


    List<Categories> getCat(){
        List<Categories> categories=new ArrayList<>();

        categories.add(new Categories(1,"beer","",0,false));
        categories.add(new Categories(1,"beer","",0,false));

        categories.add(new Categories(1,"beer","",0,false));
        categories.add(new Categories(1,"beer","",0,false));
        categories.add(new Categories(1,"beer","",0,false));
        categories.add(new Categories(1,"beer","",0,false));
        categories.add(new Categories(1,"beer","",0,false));
        categories.add(new Categories(1,"beer","",0,false));
        return  categories;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
