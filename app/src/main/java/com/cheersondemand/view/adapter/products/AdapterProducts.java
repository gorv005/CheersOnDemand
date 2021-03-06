package com.cheersondemand.view.adapter.products;

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

public class AdapterProducts extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<AllProduct> horizontalList;
    Activity context;
    ImageLoader imageLoader;
    int source;
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
        btnAddedToCart= (View) view.findViewById(R.id.btnAddedToCart);
        ivProductImage = (ImageView) view.findViewById(R.id.ivProductImage);
        rlProduct = (View) view.findViewById(R.id.rlProduct);
        btnAddToCart = (View) view.findViewById(R.id.btnAddToCart);
        rlMinus = (View) view.findViewById(R.id.rlMinus);
        rlPlus = (View) view.findViewById(R.id.rlPlus);
        llQuantity = (View) view.findViewById(R.id.llQuantity);

        ivLike= (ImageView) view.findViewById(R.id.ivLike);
    }
}

   public void modifyList(){
        notifyDataSetChanged();
    }
    public AdapterProducts(int source, List<AllProduct> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.source=source;
        this.context=context;
        imageLoader=new ImageLoader(context);
    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_grid_item, parent, false);

        return new ItemViewHolder(itemView);
    }

    else
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            AllProduct allProduct= StoreProducts.getInstance().getProduct(horizontalList.get(position).getId());
            if(allProduct==null){
                allProduct=horizontalList.get(position);
            }
            else {
                horizontalList.set(position,allProduct);
            }
            itemViewHolder.tvProductName.setText(allProduct.getName());
            if(allProduct.getOnSale()){
                itemViewHolder.tvProductPrice.setText("$"+allProduct.getSalePrice());
            }
            else {
                itemViewHolder.tvProductPrice.setText("$"+allProduct.getPrice());
            }
          //  imageLoader.DisplayImage(horizontalList.get(position).getImage(),itemViewHolder.ivProductImage);
            Util.setImage(context,allProduct.getImage(),itemViewHolder.ivProductImage);

             if(allProduct.getIsInCart()){
                 itemViewHolder.btnAddToCart.setVisibility(View.GONE);
                 itemViewHolder.btnAddedToCart.setVisibility(View.VISIBLE);
                 itemViewHolder.llQuantity.setVisibility(View.GONE);
                 itemViewHolder.tvQuantity.setText( context.getString(R.string.qty)+" "+allProduct.getCartQunatity());
             }
             else {
                 itemViewHolder.btnAddToCart.setVisibility(View.VISIBLE);
                 itemViewHolder.btnAddedToCart.setVisibility(View.GONE);
                 itemViewHolder.llQuantity.setVisibility(View.GONE);
             }

             if(allProduct.getIsWishlisted()){
                 itemViewHolder.ivLike.setImageResource(R.drawable.like);
             }
             else {
                 itemViewHolder.ivLike.setImageResource(R.drawable.unlike);

             }
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
            itemViewHolder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                         if(source==C.FRAGMENT_PRODUCT_DESC) {
                             ((ActivityContainer) context).addToCart(0, position, true);
                         }
                         else {
                             ((ActivityHome) context).addToCart(0, position, true);
                         }

                }
            });
            itemViewHolder.rlPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(source==C.FRAGMENT_PRODUCT_DESC) {
                        ((ActivityContainer) context).updateCart(0, position, true);
                    }
                    else {
                        ((ActivityHome) context).updateCart(0, position, true);
                    }

                }
            });
            itemViewHolder.rlMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(source==C.FRAGMENT_PRODUCT_DESC) {
                        ((ActivityContainer) context).updateCart(0, position, false);
                    }
                    else {
                        ((ActivityHome) context).updateCart(0, position, false);
                    }

                }
            });
            itemViewHolder.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(source==C.FRAGMENT_PRODUCT_DESC) {
                        ((ActivityContainer) context).wishListUpdate(0, position, !horizontalList.get(position).getIsWishlisted());
                    }
                    else {
                        ((ActivityHome) context).wishListUpdate(0, position, !horizontalList.get(position).getIsWishlisted());

                    }
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
