package com.cheersondemand.view.adapter.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.order.addtocart.OrderItem;

import java.util.List;


public class AdapterProductAmount extends BaseAdapter{


    private final LayoutInflater mInflater;
    private Context activity;
    private List<OrderItem> Items;
    int pos=-1;
    private int lastCheckedPosition = -1;

    public AdapterProductAmount(Context activity, List<OrderItem> item) {
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
    public OrderItem getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_product_charges_detail, null);
            holder = new ItemViewHolder();
            holder.tvProductname = (TextView) convertView.findViewById(R.id.tvProductname);
            holder.tvProductAmount = (TextView) convertView.findViewById(R.id.tvProductAmount);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        OrderItem model=Items.get(position);

        initializeViews(model, holder, position);

        return convertView;

    }
  

    private void initializeViews(final OrderItem model, final ItemViewHolder holder, final int position) {
        ((ItemViewHolder)holder).tvProductname.setText(model.getProductName());
        ((ItemViewHolder)holder).tvProductAmount.setText(activity.getString(R.string.doller)+model.getTotalPrice());
    }
    

    public static class ItemViewHolder {

        private TextView tvProductname,tvProductAmount;


    }

}
