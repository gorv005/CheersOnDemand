package com.cheersondemand.view.adapter.filter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.SideMenuItem;

import java.util.List;


public class AdapterSideFilters extends BaseAdapter{


    private final LayoutInflater mInflater;
    private Activity activity;
    private List<SideMenuItem> Items;
    int pos=-1;
    private int lastCheckedPosition = -1;
    int p=0;
    public AdapterSideFilters(Activity activity, List<SideMenuItem> item) {
        this.activity = activity;
        this.Items = item;
        mInflater = LayoutInflater.from(activity);
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
    public SideMenuItem getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_side_filter, null);
            holder = new ItemViewHolder();
            holder.tvMenuName = (TextView) convertView.findViewById(R.id.tvMenuName);
            holder.rlItem = (View) convertView.findViewById(R.id.rlItem);


            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        SideMenuItem model=Items.get(position);

        initializeViews(model, holder, position);

        return convertView;

    }
  

    public void setP(int p){
        this.p=p;
        notifyDataSetChanged();
    }
    private void initializeViews(final SideMenuItem model, final ItemViewHolder holder, final int position) {
        ((ItemViewHolder)holder).tvMenuName.setText(model.getNameResourse());
        if(position==p){
            ((ItemViewHolder)holder).rlItem.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
        }
        else {
            ((ItemViewHolder)holder).rlItem.setBackgroundColor(ContextCompat.getColor(activity, R.color.edit_text_border));

        }

    }
    

    public static class ItemViewHolder {

        private TextView tvMenuName;

        private View rlItem;

    }

}
