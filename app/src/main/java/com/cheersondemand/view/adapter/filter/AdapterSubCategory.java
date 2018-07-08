package com.cheersondemand.view.adapter.filter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.SubCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AB on 7/2/2018.
 */

public class AdapterSubCategory extends BaseAdapter implements Filterable {


    private final LayoutInflater mInflater;
    private Activity activity;
    private List<SubCategory> Items;
    int pos=-1;
    private int lastCheckedPosition = -1;
    List<SubCategory> filterList;
    CategoryFilter filter;

    public AdapterSubCategory(Activity activity, List<SubCategory> sideMenuItems) {
        this.activity = activity;
        this.Items = sideMenuItems;
        mInflater = LayoutInflater.from(activity);
        this.filterList=sideMenuItems;
    }

    @Override
    public int getViewTypeCount() {
        return Items.size();
    }

    public List<SubCategory> getSubCategoryList(){
        return filterList;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public SubCategory getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ItemViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_category, null);
            holder = new ItemViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.rlStoreItem = (RelativeLayout) convertView.findViewById(R.id.rlStoreItem);
            holder.checkBox.setClickable(false);
            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        holder.name.setText(Items.get(position).getName());
        if(Items.get(position).isSelected()) {
            holder.checkBox.setChecked(true);
            holder.name.setTextColor(ContextCompat.getColor(activity, R.color.red));
        }

        holder.rlStoreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Items.get(position).isSelected()){
                    holder.checkBox.setChecked(false);
                    holder.name.setTextColor(ContextCompat.getColor(activity, R.color.brand_text_color));
                    Items.get(position).setSelected(false);
                    filterList.get(Items.get(position).getPos()).setSelected(false);

                }
                else {
                    holder.checkBox.setChecked(true);
                    holder.name.setTextColor(ContextCompat.getColor(activity, R.color.red));
                    Items.get(position).setSelected(true);
                    filterList.get(Items.get(position).getPos()).setSelected(true);

                }
            }
        });

        return convertView;

    }
    public SubCategory getSelectedItem() {
        if(pos!=-1) {
            return filterList.get(pos);
        }
        return null;
    }

    /*private void initializeViews(final Categories model, final ItemViewHolder holder, final int position) {
        ((ItemViewHolder)holder).name.setText(model.getName());
        if (model.getId() == lastCheckedPosition){
            ((ItemViewHolder)holder).checkBox.setChecked(true);
            ((ItemViewHolder)holder).name.setTextColor(ContextCompat.getColor(activity, R.color.profile_text_color));

        }else{
            ((ItemViewHolder)holder).checkBox.setChecked(false);
            ((ItemViewHolder)holder).name.setTextColor(ContextCompat.getColor(activity, R.color.profile_email_color));

        }
        ((ItemViewHolder)holder).checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = model.getId();
                pos=position;
                notifyDataSetChanged();
            }
        });
        ((ItemViewHolder)holder).rlStoreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = model.getId();
                pos=position;
                notifyDataSetChanged();

            }
        });
    }*/

    @Override
    public Filter getFilter() {
        if(filter == null)
        {
            filter=new CategoryFilter();
        }

        return filter;
    }

    public  class ItemViewHolder {

        private TextView name;
        private CheckBox checkBox;
        private RelativeLayout rlStoreItem;


    }

    class CategoryFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toLowerCase();

                List<SubCategory> filters=new ArrayList<SubCategory>();

                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getName().toLowerCase().contains(constraint))
                    {
                        SubCategory p=new SubCategory(i, filterList.get(i).getId(),filterList.get(i).getName(),filterList.get(i).getCategoryId(),filterList.get(i).isSelected());

                        filters.add(p);
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }else
            {
                results.count=filterList.size();
                results.values=filterList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub

            Items=(ArrayList<SubCategory>) results.values;
            notifyDataSetChanged();
        }

    }
}
