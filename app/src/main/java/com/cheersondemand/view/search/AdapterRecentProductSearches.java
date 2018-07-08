package com.cheersondemand.view.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.search.RecentSearch;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivitySearchProducts;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterRecentProductSearches extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<RecentSearch> horizontalList;
    Context context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvMainAdress;
    RelativeLayout rlRecentSearch;
    public ItemViewHolder(View view) {
        super(view);
        tvMainAdress = (TextView) view.findViewById(R.id.tvMainAddress);
        rlRecentSearch = (RelativeLayout) view.findViewById(R.id.rlRecentSearch);

    }
}


    public AdapterRecentProductSearches(List<RecentSearch> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
        imageLoader=new ImageLoader(context);

    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_search_address, parent, false);

        return new ItemViewHolder(itemView);
    }

    else return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.tvMainAdress.setText(horizontalList.get(position).getName());

            itemViewHolder.rlRecentSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(horizontalList.get(position).getClassName().equals("Category")) {
                        Intent intent = new Intent(context, ActivityContainer.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(C.CAT_ID, "" + horizontalList.get(position).getClassId());
                        bundle.putString(C.SUB_CAT_ID,"");

                        bundle.putInt(C.SOURCE,C.FRAGMENT_CATEGORIES_HOME);
                        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_LISTING);
                        intent.putExtra(C.BUNDLE, bundle);
                        context.startActivity(intent);
                    }
                    else if(horizontalList.get(position).getClassName().equals("SubCategory")) {
                        Intent intent = new Intent(context, ActivityContainer.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(C.CAT_ID, "");
                        bundle.putString(C.SUB_CAT_ID,""+horizontalList.get(position).getClassId());

                        bundle.putInt(C.SOURCE,C.FRAGMENT_CATEGORIES_HOME);
                        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_LISTING);
                        intent.putExtra(C.BUNDLE, bundle);
                        context.startActivity(intent);
                    }
                   else if(horizontalList.get(position).getClassName().equals("Product")) {
                        ((ActivitySearchProducts)context).getProductDesc(horizontalList.get(position).getName(),
                                horizontalList.get(position).getClassName(),""+horizontalList.get(position).getClassId());
                    }
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
        return horizontalList.size();
    }

}
