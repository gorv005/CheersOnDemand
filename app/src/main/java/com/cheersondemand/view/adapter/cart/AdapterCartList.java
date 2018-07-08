package com.cheersondemand.view.adapter.cart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.order.addtocart.CartProduct;
import com.cheersondemand.model.order.addtocart.OrderItem;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityHome;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterCartList extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<OrderItem> horizontalList;
    Context context;
    ImageLoader imageLoader;
    CartProduct cartProduct;
    AdapterProductAmount adapterProductAmount;
    int source;
    public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName,tvSubName,tvPrice,tvQuantity,tvAddToWishList;
    public CircleImageView ivProductImage;
    ImageView imgProduct,ivLike;
    View rlMinus,rlPlus,rlAddToWishList,llRemove,rlCard,llProductNotAvailable;
    public ItemViewHolder(View view) {
        super(view);
        tvName = (TextView) view.findViewById(R.id.tvName);
        ivProductImage = (CircleImageView) view.findViewById(R.id.ivProductImage);
        imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        ivLike = (ImageView) view.findViewById(R.id.ivLike);
        tvAddToWishList = (TextView) view.findViewById(R.id.tvAddToWishList);

        tvSubName = (TextView) view.findViewById(R.id.tvSubName);
        tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        rlMinus = (View) view.findViewById(R.id.rlMinus);
        tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
        rlPlus = (View) view.findViewById(R.id.rlPlus);
        rlAddToWishList = (View) view.findViewById(R.id.rlAddToWishList);
        llRemove = (View) view.findViewById(R.id.llRemove);
        rlCard = (View) view.findViewById(R.id.rlCard);
        llProductNotAvailable = (View) view.findViewById(R.id.llProductNotAvailable);


    }
}

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        ListView lvCharges;
        TextView tvTaxes,tvDelieveryCharges,tvSubTotal,tvFinalAmount,tvCouponAmount,tvCouponName,tvChangeCoupon,tvTotalAmount,tvApplyCoupon;
        View rlAfterApplyCoupon,rlApplyCoupon;
        ImageView ivDelete;
        public FooterViewHolder(View view) {
            super(view);
            lvCharges = (ListView) view.findViewById(R.id.lvCharges);
            tvTaxes = (TextView) view.findViewById(R.id.tvTaxes);
            tvDelieveryCharges = (TextView) view.findViewById(R.id.tvDelieveryCharges);
            tvSubTotal = (TextView) view.findViewById(R.id.tvSubTotal);
            tvCouponAmount = (TextView) view.findViewById(R.id.tvCouponAmount);
            tvCouponName = (TextView) view.findViewById(R.id.tvCouponName);
            tvChangeCoupon = (TextView) view.findViewById(R.id.tvChangeCoupon);
            tvTotalAmount = (TextView) view.findViewById(R.id.tvTotalAmount);
            rlAfterApplyCoupon = (View) view.findViewById(R.id.rlAfterApplyCoupon);
            rlApplyCoupon = (View) view.findViewById(R.id.rlApplyCoupon);
            tvApplyCoupon = (TextView) view.findViewById(R.id.tvApplyCoupon);
            ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
            tvFinalAmount = (TextView) view.findViewById(R.id.tvFinalAmount);


        }
    }
    public AdapterCartList(CartProduct cartProduct,List<OrderItem> horizontalList, Activity context,int source) {
        this.horizontalList = horizontalList;
        this.context=context;
        this.cartProduct=cartProduct;
        imageLoader=new ImageLoader(context);
        this.source=source;
    }
    public void setData(CartProduct data,List<OrderItem> horizontalList){
        this.cartProduct=data;
        this.horizontalList.clear();
        this.horizontalList=horizontalList;
    }
    public void setData1(CartProduct data,List<OrderItem> horizontalList){
        this.cartProduct=null;

        this.cartProduct=data;
        this.horizontalList.clear();
        this.horizontalList=horizontalList;
        notifyDataSetChanged();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        return new ItemViewHolder(itemView);
    }
    else if (viewType == TYPE_FOOTER) {
        //Inflating footer view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_products_and_charges, parent, false);
        return new FooterViewHolder(itemView);
    }
    else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;


           OrderItem orderItem= horizontalList.get(position);
            itemViewHolder.tvName.setText(orderItem.getProduct().getName());
            Util.setImage(context,orderItem.getProduct().getImage(),((ItemViewHolder) holder).imgProduct);
            itemViewHolder.tvPrice.setText("$"+orderItem.getUnitPrice());
            itemViewHolder.tvQuantity.setText(""+orderItem.getQuantity());
            if(!orderItem.getIsDeliverable()){
                itemViewHolder.rlCard.setBackgroundResource(R.drawable.card_border);
                itemViewHolder.llProductNotAvailable.setVisibility(View.VISIBLE);
            }
            else {
                itemViewHolder.rlCard.setBackgroundResource(0);

                itemViewHolder.llProductNotAvailable.setVisibility(View.GONE);

            }

            if(orderItem.getProduct().getIsWishlisted()){
                itemViewHolder.ivLike.setImageResource(R.drawable.like);
                itemViewHolder.tvAddToWishList.setText(context.getString(R.string.added_to_wishList));
            }
            else {
                itemViewHolder.ivLike.setImageResource(R.drawable.unlike);
                itemViewHolder.tvAddToWishList.setText(context.getString(R.string.add_to_wishlist));

            }

            itemViewHolder.llRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateCartRequest updateCartRequest=new UpdateCartRequest();
                    updateCartRequest.setUuid(Util.id(context));
                    updateCartRequest.setProductId(horizontalList.get(position).getProduct().getId());
                    if(source==C.FRAGMENT_PRODUCTS_HOME) {
                        ((ActivityHome) context).removeFromCart(updateCartRequest);
                    }
                    else {
                        ((ActivityContainer) context).removeFromCart(updateCartRequest);

                    }
                }
            });
            itemViewHolder.rlPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(source==C.FRAGMENT_PRODUCTS_HOME) {

                        ((ActivityHome) context).updateCart(0, position, true);
                    }
                    else {
                        ((ActivityContainer) context).updateCart(0, position, true);

                    }
                }
            });
            itemViewHolder.rlMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(source==C.FRAGMENT_PRODUCTS_HOME) {
                        ((ActivityHome) context).updateCart(0, position, false);
                    }
                    else {
                        ((ActivityContainer) context).updateCart(0, position, false);

                    }
                }
            });
            itemViewHolder.rlAddToWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(horizontalList.get(position).getProduct().getIsWishlisted()) {
                        if(source==C.FRAGMENT_PRODUCTS_HOME) {
                            ((ActivityHome) context).wishListUpdate(0, position, false);
                        }
                        else {
                            ((ActivityContainer) context).wishListUpdate(0, position, false);

                        }
                    }
                    else {
                        if(source==C.FRAGMENT_PRODUCTS_HOME) {
                            ((ActivityHome) context).wishListUpdate(0, position, true);
                        }
                        else {
                            ((ActivityContainer) context).wishListUpdate(0, position, true);

                        }

                    }

                }
            });
        }
        else if (holder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            adapterProductAmount=new AdapterProductAmount(context,horizontalList);
            footerViewHolder.lvCharges.setAdapter(adapterProductAmount);
            Util.setListViewHeightBasedOnChildren(footerViewHolder.lvCharges);
            footerViewHolder.tvTaxes.setText(context.getString(R.string.doller)+"0.0");
            footerViewHolder.tvDelieveryCharges.setText(context.getString(R.string.doller)+"0.0");
            footerViewHolder.tvTotalAmount.setText(context.getString(R.string.doller)+""+Util.get2Decimal(cartProduct.getOrder().getTotal()));

            footerViewHolder.tvSubTotal.setText(context.getString(R.string.subtotal)+"   "+context.getString(R.string.doller)+Util.get2Decimal(cartProduct.getOrder().getSubTotal()));

            if(cartProduct.getOrder().getCoupon()!=null && cartProduct.getOrder().getAppliedDiscount()>0){
                footerViewHolder.rlAfterApplyCoupon.setVisibility(View.VISIBLE);
                footerViewHolder.rlApplyCoupon.setVisibility(View.GONE);
                footerViewHolder.tvCouponAmount.setText("-"+context.getString(R.string.doller)+cartProduct.getOrder().getAppliedDiscount());

                footerViewHolder.tvCouponName.setText(""+cartProduct.getOrder().getCoupon().getCode());
                footerViewHolder.tvFinalAmount.setText(context.getString(R.string.doller)+""+Util.get2Decimal(cartProduct.getOrder().getSubTotal()));

            }

            footerViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(source==C.FRAGMENT_PRODUCTS_HOME) {

                        ((ActivityHome) context).removeCoupon();
                    }
                    else {
                        ((ActivityContainer) context).removeCoupon();

                    }
                }
            });
            footerViewHolder.tvChangeCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context, ActivityContainer.class);
                    intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_COUPON_LIST);
                    Bundle bundle=new Bundle();
                    bundle.putString(C.CART_VALUE,""+cartProduct.getOrder().getSubTotal());
                    bundle.putString(C.COUPON_NAME,""+cartProduct.getOrder().getCoupon().getCode());

                    intent.putExtra(C.BUNDLE,bundle);
                    context.startActivity(intent);
                }
            });
          footerViewHolder.tvApplyCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context, ActivityContainer.class);
                    intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_COUPON_LIST);
                    Bundle bundle=new Bundle();
                    bundle.putString(C.CART_VALUE,""+cartProduct.getOrder().getSubTotal());
                    bundle.putString(C.COUPON_NAME,"");

                    intent.putExtra(C.BUNDLE,bundle);
                    context.startActivity(intent);
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
        return horizontalList.size()+1;
    }

}
