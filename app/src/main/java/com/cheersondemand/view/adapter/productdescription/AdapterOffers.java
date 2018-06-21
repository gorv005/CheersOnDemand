package com.cheersondemand.view.adapter.productdescription;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.productdescription.Offers;

import java.util.List;


public class AdapterOffers extends BaseAdapter{


    private final LayoutInflater mInflater;
    private Activity activity;
    private List<Offers> Items;
    int pos=-1;
    private int lastCheckedPosition = -1;

    public AdapterOffers(Activity activity, List<Offers> item) {
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
    public Offers getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_offers, null);
            holder = new ItemViewHolder();
            holder.tvOfferName = (TextView) convertView.findViewById(R.id.tvOfferName);
            holder.tvOfferSubName = (TextView) convertView.findViewById(R.id.tvOfferSubName);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        Offers model=Items.get(position);

        initializeViews(model, holder, position);

        return convertView;

    }
  

    private void initializeViews(final Offers model, final ItemViewHolder holder, final int position) {
        ((ItemViewHolder)holder).tvOfferSubName.setText(model.getSubName());
        ((ItemViewHolder)holder).tvOfferName.setText(model.getName());
    }
    

    public static class ItemViewHolder {

        private TextView tvOfferName,tvOfferSubName;


    }

}
