package com.cheersondemand.model.explore;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.Categories;

import java.util.ArrayList;
import java.util.List;

public class SpinnerCategoryAdapter extends ArrayAdapter<Categories> {

    private Activity activity;
    private ArrayList data;

    List<Categories> categories = null;
    LayoutInflater inflater;

    /*************  CustomAdapter Constructor *****************/
    public SpinnerCategoryAdapter(
            Activity activitySpinner,
            int textViewResourceId,
             List<Categories> objects
    ) {
        super(activitySpinner, textViewResourceId, objects);

        /********** Take passed values **********/
        activity = activitySpinner;
        categories = objects;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_item_new, parent, false);

        /***** Get each Model object from Arraylist ********/
        Categories category = categories.get(position);

        TextView textView = (TextView) row.findViewById(R.id.tv_spinner_text);

        // Set values for spinner each row
        textView.setText(category.getName());

        return row;
    }

}
