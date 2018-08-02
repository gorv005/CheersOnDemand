package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.view.ActivityContainer;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearchProductResults extends Fragment {

    String searchString;

    public FragmentSearchProductResults() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchString = getArguments().getString(C.PRODUCT);

    }

    @Override
    public void onResume() {
        super.onResume();

        ((ActivityContainer) getActivity()).showToolBar();
         ActivityContainer.tvTitle.setText(searchString);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_product_results, container, false);
    }

}
