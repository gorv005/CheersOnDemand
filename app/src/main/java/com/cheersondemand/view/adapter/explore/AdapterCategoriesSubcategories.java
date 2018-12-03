package com.cheersondemand.view.adapter.explore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.explore.SubCategoryExplore;
import com.cheersondemand.util.C;
import com.cheersondemand.util.Util;
import com.cheersondemand.util.itemdecoration.GridSpacingItemDecoration;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.fragments.FragmentCategorySubCategory;

import java.util.List;

public class AdapterCategoriesSubcategories extends BaseExpandableListAdapter {
    Activity activity;
    List<Categories> categories;
    private LayoutInflater inflater;

    public AdapterCategoriesSubcategories(Activity activity, List<Categories> categories) {
        this.activity=activity;
        this.categories=categories;
        inflater = LayoutInflater.from(activity);
    }
    @Override
    public int getGroupTypeCount() {
        return categories.size();
    }
    public int getGroupType(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return categories.get(groupPosition).getSubCategories();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.parent_header, parent, false);
            holder = new ViewHolder();
            holder.tvParent = (TextView) convertView.findViewById(R.id.tvParent);
            holder.ivIndicator = (ImageView) convertView.findViewById(R.id.ivIndicator);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Categories category=(Categories)getGroup(groupPosition);
         holder.tvParent.setText(category.getName());
        if (isExpanded) {
            holder.ivIndicator.setImageResource(R.drawable.up_arrow_expandable);
            //  textView.setTextColor(convertView.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.ivIndicator.setImageResource(R.drawable.down_expandable);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.child_layout, parent, false);
        RecyclerView rvSubCategory = (RecyclerView) convertView.findViewById(R.id.rvSubCategory);
        rvSubCategory.setLayoutManager(new GridLayoutManager(activity, 2));
        rvSubCategory.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(1, activity), true));
        rvSubCategory.setItemAnimator(new DefaultItemAnimator());
       // List<SubCategoryExplore> subCategoryExplores=(List<SubCategoryExplore>)getChild(groupPosition,childPosition);
        AdapterSubCategories  adapterSubCategories = new AdapterSubCategories(true, ""+categories.get(groupPosition).getId(),(List<SubCategoryExplore>)getChild(groupPosition,childPosition), activity);
        rvSubCategory.setAdapter(adapterSubCategories);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    static class ViewHolder {
        TextView tvParent;
        ImageView ivIndicator;
    }
}
