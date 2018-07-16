package com.cheersondemand.view.adapter.orderList;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.order.addtocart.Order;
import com.cheersondemand.model.order.addtocart.OrderItem;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterOrderList extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<Order> horizontalList;
    Activity context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvOrderNo,tvProductName,tvQuantity,tvDays,tvMoreProducts;
    public ImageView ivProductImage,ivDot;
    Button btnReorder,btnCancel,btnViewStatus;
    RelativeLayout rlProduct;
    public ItemViewHolder(View view) {
        super(view);
        tvOrderNo = (TextView) view.findViewById(R.id.tvOrderNo);
        tvDays = (TextView) view.findViewById(R.id.tvDays);
        tvMoreProducts = (TextView) view.findViewById(R.id.tvMoreProducts);

        tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
        ivProductImage = (ImageView) view.findViewById(R.id.ivProductImage);
        ivDot = (ImageView) view.findViewById(R.id.ivDot);
        btnReorder = (Button) view.findViewById(R.id.btnReorder);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnViewStatus = (Button) view.findViewById(R.id.btnViewStatus);

        rlProduct = (RelativeLayout) view.findViewById(R.id.rlProduct);
    }
}


    public AdapterOrderList(List<Order> horizontalList, Activity context) {
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
                .inflate(R.layout.item_order_prducts, parent, false);

        return new ItemViewHolder(itemView);
    }

    else
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            if(horizontalList.get(position).getStatus().equals(C.placed)) {
                itemViewHolder.tvDays.setText(context.getString(R.string.placed));
                itemViewHolder.ivDot.setImageResource(R.drawable.ic_black_dot);
                itemViewHolder.btnCancel.setVisibility(View.VISIBLE);
                itemViewHolder.btnReorder.setVisibility(View.GONE);
                itemViewHolder.btnViewStatus.setVisibility(View.GONE);
            }
            else  if(horizontalList.get(position).getStatus().equals(C.cancelled)) {
                itemViewHolder.tvDays.setText(horizontalList.get(position).getStatus());
                itemViewHolder.btnCancel.setVisibility(View.GONE);
                itemViewHolder.ivDot.setImageResource(R.drawable.ic_red_dot);
                itemViewHolder.btnReorder.setVisibility(View.GONE);
                itemViewHolder.btnViewStatus.setVisibility(View.GONE);
            }
            else  if(horizontalList.get(position).getStatus().equals(C.delivered)) {
                itemViewHolder.tvDays.setText(context.getString(R.string.delivered_on)+" "+horizontalList.get(position).getDeliveredOn());
                itemViewHolder.btnCancel.setVisibility(View.GONE);
                itemViewHolder.ivDot.setImageResource(R.drawable.ic_green_dot);

                itemViewHolder.btnReorder.setVisibility(View.VISIBLE);
                itemViewHolder.btnViewStatus.setVisibility(View.GONE);
            }
            else  if(horizontalList.get(position).getStatus().equals(C.confirmed)) {
                itemViewHolder.tvDays.setText(context.getString(R.string.confirmed));
                itemViewHolder.btnCancel.setVisibility(View.GONE);
                itemViewHolder.ivDot.setImageResource(R.drawable.ic_black_dot);

                itemViewHolder.btnReorder.setVisibility(View.VISIBLE);
                itemViewHolder.btnViewStatus.setVisibility(View.GONE);
            }
            else {
                itemViewHolder.btnCancel.setVisibility(View.GONE);

                itemViewHolder.btnReorder.setVisibility(View.GONE);
                itemViewHolder.btnViewStatus.setVisibility(View.GONE);
            }
            itemViewHolder.tvOrderNo.setText(horizontalList.get(position).getOrderNumber());
            if(horizontalList.get(position).getOrderItems()!=null && horizontalList.get(position).getOrderItems().size()>0) {


                if (horizontalList.get(position).getOrderItems().size() > 0) {
                    itemViewHolder.tvProductName.setText(horizontalList.get(position).getOrderItems().get(0).getProductName());
                    itemViewHolder.tvQuantity.setText(context.getString(R.string.quantity) + ": " + horizontalList.get(position).getOrderItems().get(0).getQuantity());
                    Util.setImage(context, horizontalList.get(position).getOrderItems().get(0).getProductImage(), itemViewHolder.ivProductImage);
                    if (horizontalList.get(position).getOrderItems().size() > 1) {
                        itemViewHolder.tvMoreProducts.setText("+" + (horizontalList.get(position).getOrderItems().size() - 1) + " " + context.getString(R.string.more_products));
                    } else {
                        itemViewHolder.tvMoreProducts.setText("");
                    }
                }
                else {
                    itemViewHolder.tvProductName.setText("");
                    itemViewHolder.tvQuantity.setText("0");
                    Util.setImage(context, horizontalList.get(position).getOrderItems().get(0).getProductImage(), itemViewHolder.ivProductImage);
                    itemViewHolder.tvMoreProducts.setText("");

                }
            }
            itemViewHolder.tvMoreProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog(horizontalList.get(position).getOrderItems());
                }
            });
            itemViewHolder.rlProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(C.ORDER,horizontalList.get(position));
                    ((ActivityContainer)context).fragmnetLoader(C.FRAGMENT_ORDER_DETAIL,bundle);
                }
            });

            itemViewHolder.btnReorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivityContainer)context).reorder(horizontalList.get(position));
                }
            });
            itemViewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivityContainer)context).cancelOrder(horizontalList.get(position));
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

    void dialog(List<OrderItem> orderItem) {
        LinearLayoutManager layoutManager;

        final Dialog dialog = new Dialog(context, R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.more_order_dialog); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message

        TextView title = (TextView) dialog.findViewById(R.id.titleOrders);
        RecyclerView rvOrders=(RecyclerView)dialog.findViewById(R.id.rvOrders) ;
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvOrders.setLayoutManager(layoutManager);
        rvOrders.setHasFixedSize(true);
        title.setText((orderItem.size()-1)+" "+context.getString(R.string.more_products));
        ImageView cross = (ImageView) dialog.findViewById(R.id.ivCross);
        AdapterOrderItems adapterOrderItems=new AdapterOrderItems(orderItem,context);
        rvOrders.setAdapter(adapterOrderItems);

        cross.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
