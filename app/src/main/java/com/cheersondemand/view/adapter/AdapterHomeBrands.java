package com.cheersondemand.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.Categories;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.Util;
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
    Activity context;
    ImageLoader imageLoader;
    private List<Categories> horizontalList;
    boolean isViewMore;
    int source;
    public AdapterHomeBrands(int source,boolean isViewMore,List<Categories> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.isViewMore=isViewMore;
        this.context = context;
        this.source=source;
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_item_footer, parent, false);
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
                    Util.hideKeyboard(context);

                    Intent intent = new Intent(context, ActivityContainer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(C.CAT_ID, "" + horizontalList.get(position).getId());
                    bundle.putString(C.SUB_CAT_ID, "");
                    bundle.putBoolean(C.IS_ON_SALE, false);

                    bundle.putInt(C.SOURCE, C.FRAGMENT_CATEGORIES_HOME);
                    intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_LISTING);
                    intent.putExtra(C.BUNDLE, bundle);
                    context.startActivity(intent);
                }
            });
            itemViewHolder.ivProductImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.hideKeyboard(context);

                    Intent intent = new Intent(context, ActivityContainer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(C.CAT_ID, "" + horizontalList.get(position).getId());
                    bundle.putString(C.SUB_CAT_ID, "");
                    bundle.putBoolean(C.IS_ON_SALE, false);

                    bundle.putInt(C.SOURCE, C.FRAGMENT_CATEGORIES_HOME);
                    intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_LISTING);
                    intent.putExtra(C.BUNDLE, bundle);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.rlViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.hideKeyboard(context);

                    /*Intent intent = new Intent(context, ActivityContainer.class);
                    intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_CATEGORIES);
                    context.startActivity(intent);*/
                    if(source==C.FRAGMENT_PRODUCTS_HOME) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCTS_HOME);
                        ((ActivityHome) context).fragmnetLoader(C.FRAGMENT_CATEGORIES, bundle);
                    }
                   else if(source==C.FRAGMENT_SEARCH_PRODUCT_RESULTS) {
                           Intent intent = new Intent(context, ActivityContainer.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(C.SOURCE, C.FRAGMENT_CATEGORIES);
                        intent.putExtra(C.BUNDLE,bundle);
                        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_CATEGORIES);
                             context.startActivity(intent);
                    }
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
        if(isViewMore) {
            return horizontalList.size() + 1;
        }
        else {
           return horizontalList.size();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBrandName;
        public ImageView ivProductImage;

        public ItemViewHolder(View view) {
            super(view);
            tvBrandName = (TextView) view.findViewById(R.id.tvBrandName);
            ivProductImage = (ImageView) view.findViewById(R.id.ivProductImage);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMore;
        View rlViewMore;
        public FooterViewHolder(View view) {
            super(view);
            ivMore = (ImageView) view.findViewById(R.id.ivProductMore);
            rlViewMore = (View) view.findViewById(R.id.rlViewMore);

        }
    }

}
