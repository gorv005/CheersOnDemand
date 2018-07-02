package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.Brand;
import com.cheersondemand.view.ActivityFilters;
import com.cheersondemand.view.adapter.filter.AdapterBrands;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBrandsFilter extends Fragment {


    @BindView(R.id.etBrandName)
    EditText etBrandName;
    @BindView(R.id.lvBrands)
    ListView lvBrands;
    @BindView(R.id.rlBrands)
    LinearLayout rlBrands;
    @BindView(R.id.LLView)
    RelativeLayout LLView;
    Unbinder unbinder;

    List<Brand> brandList;
    AdapterBrands adapterBrands;
    public FragmentBrandsFilter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brands_filter, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        brandList=  ((ActivityFilters)getActivity()).getBrandList();
        adapterBrands=new AdapterBrands(getActivity(),brandList);
        lvBrands.setAdapter(adapterBrands);
        etBrandName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //  String text = etStoreName.getText().toString().toLowerCase(Locale.getDefault());
                adapterBrands.getFilter().filter(etBrandName.getText().toString());
            }
        });
    }


    public List<Brand> getBrandList(){
       return adapterBrands.getBrandList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
