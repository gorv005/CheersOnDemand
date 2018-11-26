package com.cheersondemand.view.adapter.explore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.explore.SubCategoryExplore;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.StoreProducts;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityHome;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterSubCategories extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<SubCategoryExplore> horizontalList;
    Activity context;
    ImageLoader imageLoader;
    boolean isHome;
    public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvProductName,tvProductPrice,tvQuantity,tvAddToCart;
    public ImageView ivProductImage,ivLike;
    View rlProduct,btnAddToCart,rlMinus,rlPlus,llQuantity,btnAddedToCart;
    public ItemViewHolder(View view) {
        super(view);
        tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) view.findViewById(R.id.tvProductPrice);
        tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
        tvAddToCart = (TextView) view.findViewById(R.id.tvAddtoCart);
        btnAddedToCart = (View) view.findViewById(R.id.btnAddedToCart);

        ivProductImage = (ImageView) view.findViewById(R.id.ivProductImage);
        rlProduct = (View) view.findViewById(R.id.rlProduct);
        btnAddToCart = (View) view.findViewById(R.id.btnAddToCart);
        rlMinus = (View) view.findViewById(R.id.rlMinus);
        rlPlus = (View) view.findViewById(R.id.rlPlus);
        llQuantity = (View) view.findViewById(R.id.llQuantity);

        ivLike= (ImageView) view.findViewById(R.id.ivLike);
    }
}



    public AdapterSubCategories(boolean isHome, List<SubCategoryExplore> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
        this.isHome=isHome;
        imageLoader=new ImageLoader(context);
    }
    public void modifyList(){
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcategory_item, parent, false);

        return new ItemViewHolder(itemView);
    }
    else if (viewType == TYPE_FOOTER) {
        //Inflating footer view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_card_item_home_footer, parent, false);
        return new FooterViewHolder(itemView);
    }
    else
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.tvProductName.setText(horizontalList.get(position).getName());
          //  imageLoader.DisplayImage(horizontalList.get(position).getImage(),itemViewHolder.ivProductImage);
            Util.setImage(context,horizontalList.get(position).getImage(),itemViewHolder.ivProductImage);

            itemViewHolder.rlProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityContainer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(C.CAT_ID, "" + horizontalList.get(position).getId());
                    bundle.putString(C.SUB_CAT_ID, ""+horizontalList.get(position).getId());
                    bundle.putBoolean(C.IS_ON_SALE, false);
                    bundle.putInt(C.SOURCE, C.FRAGMENT_CATEGORIES);
                    intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_LISTING);
                    intent.putExtra(C.BUNDLE, bundle);
                    context.startActivity(intent);
                }
            });

        }
        else if (holder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.rlViewAlProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.hideKeyboard(context);

                    Intent intent=new Intent(context,ActivityContainer.class);

                    Bundle bundle=new Bundle();
                    bundle.putString(C.CAT_ID,"");
                    bundle.putString(C.SUB_CAT_ID,"");
                    bundle.putBoolean(C.IS_ON_SALE, true);
                    bundle.putInt(C.SOURCE,C.FRAGMENT_PRODUCTS_HOME);
                    intent.putExtra(C.BUNDLE,bundle);
                    intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_PRODUCT_LISTING);
                    context.startActivity(intent);

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
        return horizontalList.size();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        View rlViewAlProduct;
        public FooterViewHolder(View view) {
            super(view);
            rlViewAlProduct = (View) view.findViewById(R.id.rlViewAlProduct);

        }
    }

}
