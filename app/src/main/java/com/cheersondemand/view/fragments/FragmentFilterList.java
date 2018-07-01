package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cheersondemand.R;
import com.cheersondemand.model.SideMenuItem;
import com.cheersondemand.util.Util;
import com.cheersondemand.util.listeners.OnFilterNameSelectionChangeListener;
import com.cheersondemand.view.adapter.filter.AdapterSideFilters;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFilterList extends Fragment {


    @BindView(R.id.lvFilters)
    ListView lvFilters;
    Unbinder unbinder;
    AdapterSideFilters adapterSideFilters;
    List<SideMenuItem> sideMenuItemList;
     public FragmentFilterList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sideMenuItemList=Util.getSideFilterList();
        adapterSideFilters=new AdapterSideFilters(getActivity(), sideMenuItemList);
        lvFilters.setAdapter(adapterSideFilters);
        lvFilters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnFilterNameSelectionChangeListener listener = (OnFilterNameSelectionChangeListener) getActivity();
                listener.OnSelectionChanged(sideMenuItemList.get(position).getTag(), null);
                adapterSideFilters.setP(position);
            }
        });

    }
}
