package com.cheersondemand.view.adapter.productdescription;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;

import java.util.List;

/**
 * Created by GAURAV on 6/7/2018.
 */

public class AdapterSimilarProducts extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<AllProduct> horizontalList;
    Activity context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvProductName,tvProductPrice;
    public ImageView ivProductImage,ivLike;
    RelativeLayout rlProduct;
    public ItemViewHolder(View view) {
        super(view);
        tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) view.findViewById(R.id.tvProductPrice);
        ivProductImage = (ImageView) view.findViewById(R.id.ivProductImage);
        rlProduct = (RelativeLayout) view.findViewById(R.id.rlProduct);
        ivLike= (ImageView) view.findViewById(R.id.ivLike);
    }
}


    public AdapterSimilarProducts(List<AllProduct> horizontalList, Activity context) {
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
                .inflate(R.layout.product_item, parent, false);

        return new ItemViewHolder(itemView);
    }

    else
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.tvProductName.setText(horizontalList.get(position).getName());
            itemViewHolder.tvProductPrice.setText("$"+horizontalList.get(position).getPrice());
           // imageLoader.DisplayImage(horizontalList.get(position).getImage(),itemViewHolder.ivProductImage);
            Util.setImage(context,horizontalList.get(position).getImage(),itemViewHolder.ivProductImage);
            itemViewHolder.rlProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat OptionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation(context, itemViewHolder.ivProductImage,context.getString(R.string.shared_image));
                    Intent intent = new Intent(context, ActivityContainer.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(C.PRODUCT,horizontalList.get(position));
                    intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_PRODUCT_DESC);
                    intent.putExtra(C.BUNDLE,bundle);
                    context.startActivity(intent, OptionsCompat.toBundle());
                }
            });
          /*  itemViewHolder.tvBrandName.setText(horizontalList.get(position));

            itemViewHolder.tvBrandName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, itemViewHolder.tvBrandName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });*/
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
