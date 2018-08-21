package com.cheersondemand.view.adapter.card;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.card.CardList;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;

import java.util.List;

import ru.rambler.libs.swipe_layout.SwipeLayout;

/**
 * Created by AB on 7/10/2018.
 */

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHolder > {
    Context context;
    List<CardList> horizontalList;

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public AdapterCard(List<CardList> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        layoutId = R.layout.item_card;

        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
            @Override
            public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {
            }

            @Override
            public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {
            }

            @Override
            public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
            }

            @Override
            public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
            }
        });

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_member_name.setText(horizontalList.get(position).getCardHolderName());
        holder.tv_validity.setText(horizontalList.get(position).getExpMonth()+"/"+horizontalList.get(position).getExpYear());
        holder.tv_card_number.setText( context.getString(R.string.card_number_sample)+" "+horizontalList.get(position).getLast4());

        Util.setCardType(Util.getCardTypeUsingBrandName(horizontalList.get(position).getBrand()),holder.ivType,context);
        holder.rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityContainer)context).deleteCard(position);
                holder.swipeLayout.animateReset();

            }
        });
    }

  /*  @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
            itemsOffset[holder.getAdapterPosition()] = holder.swipeLayout.getOffset();
        }
    }*/

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView tv_card_number,tv_member_name,tv_validity;
        private final SwipeLayout swipeLayout;
        private final View rightView;
        private final ImageView ivType;
        ViewHolder(View itemView) {
            super(itemView);
            tv_card_number = (TextView) itemView.findViewById(R.id.tv_card_number);
            tv_member_name= (TextView) itemView.findViewById(R.id.tv_member_name);
            tv_validity= (TextView) itemView.findViewById(R.id.tv_validity);
            ivType=(ImageView)itemView.findViewById(R.id.ivType);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_layout);
            rightView = itemView.findViewById(R.id.right_view);
        }
    }

}
