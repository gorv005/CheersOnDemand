package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.util.seekbar.RangeSeekBar;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPriceRangeFilter extends Fragment {


    @BindView(R.id.left)
    TextView left;
    @BindView(R.id.right)
    TextView right;

    Unbinder unbinder;
    @BindView(R.id.rangeSeekbar)
    CrystalRangeSeekbar rangeSeekbar;

    public FragmentPriceRangeFilter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_price_range_filter, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RangeSeekBar<Long> rangeSeekBar = new RangeSeekBar<>(getActivity());
        // Set the range
        left.setText(getString(R.string.doller) + 0);
        left.setText(getString(R.string.doller) + 5000);
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                left.setText(getString(R.string.doller) +String.valueOf(minValue));
                right.setText(getString(R.string.doller) +String.valueOf(maxValue));
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
