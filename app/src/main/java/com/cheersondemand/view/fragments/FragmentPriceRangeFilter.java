package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.util.seekbar.RangeSeekBar;

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
        rangeSeekBar.setRangeValues(0L, 5000L);
        rangeSeekBar.setSelectedMinValue(0L);
        rangeSeekBar.setSelectedMaxValue(5000L);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Long>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<Long> bar, Long min, Long maxValue) {
                left.setText(""+min);
                right.setText(""+maxValue);
            }
        });
        // Add to layout
        FrameLayout layout = (FrameLayout)view.findViewById(R.id.seekbar_placeholder);
        layout.addView(rangeSeekBar);

        // Seek bar for which we will set text color in code
      /*  RangeSeekBar rangeSeekBarTextColorWithCode = (RangeSeekBar)view.findViewById(R.id.rangeSeekBarTextColorWithCode);
        rangeSeekBarTextColorWithCode.setTextAboveThumbsColorResource(android.R.color.holo_blue_bright);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
