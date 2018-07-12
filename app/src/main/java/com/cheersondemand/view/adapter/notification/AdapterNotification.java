package com.cheersondemand.view.adapter.notification;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.notification.Notifications;
import com.cheersondemand.view.ActivityContainer;

import java.util.List;

import ru.rambler.libs.swipe_layout.SwipeLayout;

/**
 * Created by AB on 7/10/2018.
 */

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder > {

    Context context;
    List<Notifications> horizontalList;
    private final int COUNT = 30;
   // private final int[] itemsOffset ;

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public AdapterNotification(List<Notifications> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
       // itemsOffset = new int[horizontalList.size()];
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        layoutId = R.layout.item_notifications;

        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);

        /*View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.swipeLayout.animateReset();
            }
        };*/

       /* if (viewHolder.leftView != null) {
            viewHolder.leftView.setClickable(true);
            viewHolder.leftView.setOnClickListener(onClick);
        }*/

       /* if (viewHolder.rightView != null) {
            viewHolder.rightView.setClickable(true);
            viewHolder.rightView.setOnClickListener(onClick);
        }*/

        viewHolder.swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
            @Override
            public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {
            }

            @Override
            public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {
             /*   Toast.makeText(swipeLayout.getContext(),
                        (moveToRight ? "Left" : "Right") + " clamp reached",
                        Toast.LENGTH_SHORT)
                        .show();*/
               // viewHolder.textViewPos.setText("TADA!");
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvNotificationStatus.setText(horizontalList.get(position).getTitle());
        holder.tvNotificationDesc.setText(horizontalList.get(position).getMessage());
        holder.tvDays.setText(horizontalList.get(position).getCreatedAt());
        holder.rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityContainer)context).deleteNotification(position);
            }
        });
      //  holder.swipeLayout.setOffset(itemsOffset[position]);
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

        private  TextView tvNotificationStatus,tvNotificationDesc,tvDays;
        private final SwipeLayout swipeLayout;
        private final View rightView;

        ViewHolder(View itemView) {
            super(itemView);
            tvNotificationStatus = (TextView) itemView.findViewById(R.id.tvNotificationStatus);
            tvNotificationDesc= (TextView) itemView.findViewById(R.id.tvNotificationDesc);
            tvDays= (TextView) itemView.findViewById(R.id.tvDays);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_layout);
            rightView = itemView.findViewById(R.id.right_view);
        }
    }

}
