package com.cheersondemand.view.adapter.products;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.productList.Sort;

import java.util.List;


public class AdapterSortList extends BaseAdapter{


    private final LayoutInflater mInflater;
    private Activity activity;
    private List<Sort> Items;
    int pos=-1;
    private int lastCheckedPosition = -1;

    public AdapterSortList(Activity activity, List<Sort> item,int pos) {
        this.activity = activity;
        this.Items = item;
        mInflater = LayoutInflater.from(activity);
        lastCheckedPosition=pos;
    }



   public void setTick(int id){
        lastCheckedPosition=id;
        notifyDataSetChanged();
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
    public Sort getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_sort, null);
            holder = new ItemViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.imgSort = (ImageView) convertView.findViewById(R.id.imgSort);
            holder.imgSelected = (ImageView) convertView.findViewById(R.id.imgSelected);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        Sort model=Items.get(position);

        initializeViews(model, holder, position);

        return convertView;

    }
  

    private void initializeViews(final Sort model, final ItemViewHolder holder, final int position) {
        ((ItemViewHolder)holder).tvName.setText(activity.getString(model.getName()));
        ((ItemViewHolder)holder).imgSort.setImageResource(model.getSortImage());
        if(lastCheckedPosition==model.getId()){
            ((ItemViewHolder)holder).imgSelected.setImageResource(model.getSelectedImage());
            ((ItemViewHolder)holder).imgSelected.setVisibility(View.VISIBLE);

        }
        else {
            ((ItemViewHolder)holder).imgSelected.setVisibility(View.GONE);

        }

    }
    

    public static class ItemViewHolder {

        private TextView tvName;
        private ImageView imgSort,imgSelected;

    }

}
