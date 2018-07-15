package com.cheersondemand.view.adapter.orderList;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.order.addtocart.OrderItem;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.Util;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterOrderItems extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<OrderItem> horizontalList;
    Activity context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvProductName,tvQuantity;
    public ImageView ivProductImage;
    View viewLine;
    public ItemViewHolder(View view) {
        super(view);

        tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
        ivProductImage = (ImageView) view.findViewById(R.id.ivProductImage);
        viewLine = (View) view.findViewById(R.id.viewLine);

    }
}


    public AdapterOrderItems(List<OrderItem> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
        imageLoader=new ImageLoader(context);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_more_orders, parent, false);

        return new ItemViewHolder(itemView);
    }

    else
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvProductName.setText(horizontalList.get(position).getProductName());
            itemViewHolder.tvQuantity.setText("Qty:"+horizontalList.get(position).getQuantity());
            Util.setImage(context, horizontalList.get(position).getProductImage(), itemViewHolder.ivProductImage);
            if(position==horizontalList.size()){
                itemViewHolder.viewLine.setVisibility(View.GONE);
            }
        }

    }
    @Override
    public int getItemViewType(int position) {

        return TYPE_ITEM;
    }
    @Override
    public int getItemCount() {
        return horizontalList.size();
    }


}
