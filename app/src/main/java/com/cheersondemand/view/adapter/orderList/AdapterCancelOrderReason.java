package com.cheersondemand.view.adapter.orderList;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.productdescription.Offers;
import com.cheersondemand.util.ImageLoader.ImageLoader;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterCancelOrderReason extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    Activity context;
    ImageLoader imageLoader;
    int selectedId = -1;
    private List<Offers> horizontalList;

    public AdapterCancelOrderReason(List<Offers> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context = context;
        imageLoader = new ImageLoader(context);
    }

    public int getSelectedId() {
        return selectedId;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_order_cancel_reason, parent, false);

            return new ItemViewHolder(itemView);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvName.setText(horizontalList.get(position + 1).getName());
            if (selectedId == horizontalList.get(position).getId()) {
                itemViewHolder.tvName.setTextColor(ContextCompat.getColor(context, R.color.profile_text_color));
                itemViewHolder.imgSelected.setVisibility(View.VISIBLE);
            } else {
                itemViewHolder.tvName.setTextColor(ContextCompat.getColor(context, R.color.profile_email_color));
                itemViewHolder.imgSelected.setVisibility(View.GONE);

            }
            itemViewHolder.llCancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedId = horizontalList.get(position).getId();
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return horizontalList.size() - 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView imgSelected;
        View llCancelView;

        public ItemViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tvName);
            imgSelected = (ImageView) view.findViewById(R.id.imgSelected);
            llCancelView = (View) view.findViewById(R.id.llCancelView);

        }
    }


}
