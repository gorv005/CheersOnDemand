package com.cheersondemand.view.adapter.orderList;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.order.OrderHistory;
import com.cheersondemand.util.ImageLoader.ImageLoader;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterOrderStatus extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<OrderHistory> horizontalList;
    Activity context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvOrderStatus,tvOrderDateStatus,tvOrderMsg,tvOrderDateWithTime;

    public ItemViewHolder(View view) {
        super(view);

        tvOrderStatus = (TextView) view.findViewById(R.id.tvOrderStatus);
        tvOrderDateStatus = (TextView) view.findViewById(R.id.tvOrderDateStatus);
        tvOrderMsg = (TextView) view.findViewById(R.id.tvOrderMsg);
        tvOrderDateWithTime = (TextView) view.findViewById(R.id.tvOrderDateWithTime);

    }
}


    public AdapterOrderStatus(List<OrderHistory> horizontalList, Activity context) {
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
                .inflate(R.layout.item_order_status, parent, false);

        return new ItemViewHolder(itemView);
    }

    else
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvOrderStatus.setText(horizontalList.get(position).getStatus());
            itemViewHolder.tvOrderDateStatus.setText(horizontalList.get(position).getOrderDate());
            itemViewHolder.tvOrderMsg.setText(horizontalList.get(position).getMsg());
            itemViewHolder.tvOrderDateWithTime.setText(horizontalList.get(position).getOrderDatetime());

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
