package com.cheersondemand.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.Categories;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityHome;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterHomeBrands extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    Context context;
    ImageLoader imageLoader;
    private List<Categories> horizontalList;

    public AdapterHomeBrands(List<Categories> horizontalList, Activity context) {
        this.horizontalList = horizontalList;

        this.context = context;
        imageLoader = new ImageLoader(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_round_item, parent, false);

            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_round_item_footer, parent, false);
            return new FooterViewHolder(itemView);
        } else return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.tvBrandName.setText(horizontalList.get(position).getName());
            // Util.setImage(context,horizontalList.get(position).getImage(),((ItemViewHolder) holder).ivProductImage);
            imageLoader.DisplayImage(horizontalList.get(position).getImage(), ((ItemViewHolder) holder).ivProductImage);
            itemViewHolder.tvBrandName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityContainer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(C.CAT_ID, "" + horizontalList.get(position).getId());
                    bundle.putString(C.SUB_CAT_ID, "");

                    bundle.putInt(C.SOURCE, C.FRAGMENT_CATEGORIES_HOME);
                    intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_LISTING);
                    intent.putExtra(C.BUNDLE, bundle);
                    context.startActivity(intent);
                }
            });
            itemViewHolder.ivProductImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityContainer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(C.CAT_ID, "" + horizontalList.get(position).getId());
                    bundle.putString(C.SUB_CAT_ID, "");

                    bundle.putInt(C.SOURCE, C.FRAGMENT_CATEGORIES_HOME);
                    intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_LISTING);
                    intent.putExtra(C.BUNDLE, bundle);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(context, ActivityContainer.class);
                    intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_CATEGORIES);
                    context.startActivity(intent);*/
                    ((ActivityHome)context).fragmnetLoader(C.FRAGMENT_CATEGORIES,null);
                    //   Toast.makeText(context, "More", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == horizontalList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return horizontalList.size() + 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBrandName;
        public CircularImageView ivProductImage;

        public ItemViewHolder(View view) {
            super(view);
            tvBrandName = (TextView) view.findViewById(R.id.tvBrandName);
            ivProductImage = (CircularImageView) view.findViewById(R.id.ivProductImage);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        CircularImageView ivMore;

        public FooterViewHolder(View view) {
            super(view);
            ivMore = (CircularImageView) view.findViewById(R.id.ivProductMore);
        }
    }

}
