package com.cheersondemand.view.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by GAURAV on 6/7/2018.
 */

public class AdapterCategories extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<Categories> horizontalList;
    Context context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCatName,tvNoOFBrands;
    public CircleImageView ivProductImage;
    View rlView;
    public ItemViewHolder(View view) {
        super(view);
        tvCatName = (TextView) view.findViewById(R.id.tvCatName);
        ivProductImage = (CircleImageView) view.findViewById(R.id.ivProductImage);
        tvNoOFBrands = (TextView) view.findViewById(R.id.tvNoOFBrands);
        rlView = (View) view.findViewById(R.id.rlView);

    }
}

    public AdapterCategories(List<Categories> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
        imageLoader=new ImageLoader(context);

    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categories, parent, false);

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

            itemViewHolder.tvCatName.setText(horizontalList.get(position).getName());
            itemViewHolder.tvNoOFBrands.setText(horizontalList.get(position).getProductsCount()+" "+context.getString(R.string.brands_available));

            Util.setImage(context,horizontalList.get(position).getImage(),((ItemViewHolder) holder).ivProductImage);
           // imageLoader.DisplayImage(horizontalList.get(position).getImage(),((ItemViewHolder) holder).ivProductImage);
            itemViewHolder.rlView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                    Bundle bundle=new Bundle();
                    bundle.putString(C.CAT_ID,""+horizontalList.get(position).getId());
                    bundle.putString(C.SUB_CAT_ID,"");

                    bundle.putInt(C.SOURCE,C.FRAGMENT_CATEGORIES);

                    ((ActivityContainer)context).fragmnetLoader(C.FRAGMENT_PRODUCT_LISTING,bundle);
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
