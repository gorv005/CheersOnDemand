package com.cheersondemand.view.adapter.coupon;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.coupon.CouponInfo;
import com.cheersondemand.util.ImageLoader.ImageLoader;

import java.util.List;

/**
 * Created by GAURAV on 6/7/2018.
 */

public class AdapterCouponList extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<CouponInfo> horizontalList;
    Context context;
    ImageLoader imageLoader;

    public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCouponTitle,tvCouponDesc,tvTermsAndCondition,tvApply;
    ImageView imgProduct,ivLike;
    View rlCard;
    public ItemViewHolder(View view) {
        super(view);
        tvCouponTitle = (TextView) view.findViewById(R.id.tvCouponTitle);
        tvCouponDesc = (TextView) view.findViewById(R.id.tvCouponDesc);

        tvTermsAndCondition = (TextView) view.findViewById(R.id.tvTermsAndCondition);
        tvApply = (TextView) view.findViewById(R.id.tvApply);

        rlCard = (View) view.findViewById(R.id.rlCard);


    }
}


    public AdapterCouponList(List<CouponInfo> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
        imageLoader=new ImageLoader(context);

    }
    public void setData(List<CouponInfo> horizontalList){
        this.horizontalList.clear();
        this.horizontalList=horizontalList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coupen, parent, false);

        return new ItemViewHolder(itemView);
    }

    else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;


            CouponInfo couponInfo= horizontalList.get(position);
            itemViewHolder.tvCouponTitle.setText(couponInfo.getTitle());
            itemViewHolder.tvCouponDesc.setText(couponInfo.getDescription());

        /*
            if(!orderItem.getIsDeliverable()){
                itemViewHolder.rlCard.setBackgroundResource(R.drawable.card_border);
                itemViewHolder.llProductNotAvailable.setVisibility(View.VISIBLE);
            }
            else {
                itemViewHolder.llProductNotAvailable.setVisibility(View.GONE);

            }
            */
        }

    }
    @Override
    public int getItemViewType(int position) {
        if (position == horizontalList.size() ) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

}
