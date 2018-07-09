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

import com.cheersondemand.R;
import com.cheersondemand.model.HelpCenter.HelpCenterList;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.helpcenter.AdapterHelpCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHelpCenter extends Fragment {


    @BindView(R.id.lvHelpList)
    RecyclerView lvHelpList;
    Unbinder unbinder;
    LinearLayoutManager horizontalLayout;
    AdapterHelpCenter adapterHelpCenter;
    public FragmentHelpCenter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(getString(R.string.help_center));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lvHelpList.setLayoutManager(horizontalLayout);
        lvHelpList.setHasFixedSize(true);
        adapterHelpCenter=new AdapterHelpCenter(getHelpList(),getActivity());
        lvHelpList.setAdapter(adapterHelpCenter);
    }


    public List<HelpCenterList> getHelpList(){

        List<HelpCenterList> helpCenterLists=new ArrayList<>();

        helpCenterLists.add(new HelpCenterList(getString(R.string.faq),"http://ror.anasource.com:8090/cheers_on_demand/static_pages/faq"));
        helpCenterLists.add(new HelpCenterList(getString(R.string.about_us),"http://ror.anasource.com:8090/cheers_on_demand/static_pages/faq"));
        helpCenterLists.add(new HelpCenterList(getString(R.string.contact_us),"http://ror.anasource.com:8090/cheers_on_demand/static_pages/contact-us"));
        helpCenterLists.add(new HelpCenterList(getString(R.string.how_it_works),"http://ror.anasource.com:8090/cheers_on_demand/static_pages/how-it-works"));
        helpCenterLists.add(new HelpCenterList(getString(R.string.terms_od_services),"http://ror.anasource.com:8090/cheers_on_demand/static_pages/terms-of-service"));
        helpCenterLists.add(new HelpCenterList(getString(R.string.privacy_policy),"http://ror.anasource.com:8090/cheers_on_demand/static_pages/privacy-policy"));
        //helpCenterLists.add(new HelpCenterList(getString(R.string.del),"http://ror.anasource.com:8090/cheers_on_demand/static_pages/faq"));
        return helpCenterLists;

    }
}
