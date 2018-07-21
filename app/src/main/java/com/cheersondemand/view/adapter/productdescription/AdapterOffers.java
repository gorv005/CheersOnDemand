package com.cheersondemand.view.adapter.productdescription;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.coupon.CouponInfo;

import java.util.List;


public class AdapterOffers extends BaseAdapter{


    private final LayoutInflater mInflater;
    private Activity activity;
    private List<CouponInfo> Items;
    int pos=-1;
    private int lastCheckedPosition = -1;

    public AdapterOffers(Activity activity, List<CouponInfo> item) {
        this.activity = activity;
        this.Items = item;
        mInflater = LayoutInflater.from(activity);
    }




    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public CouponInfo getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ItemViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_offers, null);
            holder = new ItemViewHolder();
            holder.tvOfferName = (TextView) convertView.findViewById(R.id.tvOfferName);
            holder.tvOfferSubName = (TextView) convertView.findViewById(R.id.tvOfferSubName);
            holder.rlItem = (RelativeLayout) convertView.findViewById(R.id.rlItem);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        CouponInfo model=Items.get(position);

        initializeViews(model, holder, position);
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(Items.get(position));
            }
        });
        return convertView;

    }
  

    private void initializeViews(final CouponInfo model, final ItemViewHolder holder, final int position) {
        ((ItemViewHolder)holder).tvOfferSubName.setText(model.getDescription());
        ((ItemViewHolder)holder).tvOfferName.setText(model.getTitle());
    }
    

    public static class ItemViewHolder {

        private TextView tvOfferName,tvOfferSubName;
        private RelativeLayout rlItem;

    }
    void dialog(final CouponInfo couponInfo) {
        final Dialog dialog = new Dialog(activity, R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.dialog_one_button); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message
        TextView title = (TextView) dialog.findViewById(R.id.tvmessagedialogtitle);
        TextView title1 = (TextView) dialog.findViewById(R.id.tvmessagedialogtitle1);

        TextView message = (TextView) dialog.findViewById(R.id.tvmessagedialogtext);
        title.setText(couponInfo.getTitle());
        title1.setText(couponInfo.getCode());

        message.setText(couponInfo.getDescription()+"\n\n"+couponInfo.getValidFrom()+" to "+ couponInfo.getValidTo()+
        "\n\n\n"+activity.getString(R.string.minimum_cart_value)+" "+couponInfo.getMinCartValue()+"\n\n"+
                activity.getString(R.string.maximum_cart_value)+" "+couponInfo.getMaxCartValue());
        Button yes = (Button) dialog.findViewById(R.id.bmessageDialogYes);
        yes.setText(activity.getString(R.string.ok));
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.show();
    }

}
