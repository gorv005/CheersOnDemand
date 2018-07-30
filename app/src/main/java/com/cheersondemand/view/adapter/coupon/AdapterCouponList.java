package com.cheersondemand.view.adapter.coupon;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.coupon.CouponInfo;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityHome;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterCouponList extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<CouponInfo> horizontalList;
    Context context;
    ImageLoader imageLoader;
    String couponName="";
    int source;
    public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCouponTitle,tvCouponDesc,tvTermsAndCondition,tvApply,tvApplied;
    View rlCard;
    public ItemViewHolder(View view) {
        super(view);
        tvCouponTitle = (TextView) view.findViewById(R.id.tvCouponTitle);
        tvCouponDesc = (TextView) view.findViewById(R.id.tvCouponDesc);

        tvTermsAndCondition = (TextView) view.findViewById(R.id.tvTermsAndCondition);
        tvApply = (TextView) view.findViewById(R.id.tvApply);
        tvApplied = (TextView) view.findViewById(R.id.tvApplied);

        rlCard = (View) view.findViewById(R.id.rlCard);


    }
}


    public AdapterCouponList(int source,List<CouponInfo> horizontalList, Activity context,String couponName) {
        this.horizontalList = horizontalList;
        this.context=context;
        this.couponName=couponName;
        imageLoader=new ImageLoader(context);
        this.source=source;
    }
    public void setData(List<CouponInfo> horizontalList,String couponName){
        this.horizontalList.clear();
        this.horizontalList=horizontalList;
        this.couponName=couponName;
      //  notifyDataSetChanged();
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
            if(couponInfo.getCouponName().equals(couponInfo.getCode())){
                itemViewHolder.rlCard.setBackgroundResource(R.drawable.coupon_border);
                itemViewHolder.tvApply.setVisibility(View.GONE);
                itemViewHolder.tvApplied.setVisibility(View.VISIBLE);
            }
            else {
                itemViewHolder.tvApply.setVisibility(View.VISIBLE);
                itemViewHolder.tvApplied.setVisibility(View.GONE);
                itemViewHolder.rlCard.setBackgroundResource(0);

            }



        /*
            if(!orderItem.getIsDeliverable()){
                itemViewHolder.rlCard.setBackgroundResource(R.drawable.card_border);
                itemViewHolder.llProductNotAvailable.setVisibility(View.VISIBLE);
            }
            else {
                itemViewHolder.llProductNotAvailable.setVisibility(View.GONE);

            }
            */
            itemViewHolder.tvApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(source== C.FRAGMENT_PRODUCT_DESC) {
                        ((ActivityContainer) context).applyCoupon(horizontalList.get(position).getCode());
                    }
                    else {
                        ((ActivityHome) context).applyCoupon(horizontalList.get(position).getCode());

                    }
                }
            });
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
